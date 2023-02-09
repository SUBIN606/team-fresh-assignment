package teamfresh.api.web.compensations.penalties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.exception.CompensationNotFoundException;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.service.PenaltyIssuer;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static teamfresh.api.web.MvcTestHelpers.toJSON;

@WebMvcTest(PenaltyIssueController.class)
public class PenaltyIssueControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PenaltyIssuer penaltyIssuer;

    @DisplayName("POST /compensations/{compensationId}/peanlties")
    @Nested
    class Describe_post_penalties {

        Long compensationId = 4L;
        Long ownerId = 11L;
        Long penaltyId = 8L;
        String content = "페널티 내용";
        PenaltyIssueController.Request request
                = new PenaltyIssueController.Request(ownerId, content);

        @DisplayName("배상정보 id와 페널티 대상 id가 주어지면")
        @Nested
        class Context_with_compensation_id_and_owner_id {
            @BeforeEach
            void setUp() {
                given(penaltyIssuer.issue(compensationId, ownerId, content))
                        .willReturn(Penalty.of(penaltyId, Compensation.of(compensationId, 100000), ownerId));
            }

            @DisplayName("201 Created 상태코드와 함께 발급된 페널티 id를 응답한다")
            @Test
            void it_returns_issued_penalty_id() throws Exception {
                mvc.perform(
                        post(String.format("/compensations/%s/penalties", compensationId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(request))
                ).andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").isNumber(),
                        jsonPath("$.id").value(penaltyId)
                );
            }
        }

        @DisplayName("찾을 수 없는 배상정보 id가 주어지면")
        @Nested
        class Context_with_not_exist_compensation_id {
            @BeforeEach
            void setUp() {
                given(penaltyIssuer.issue(compensationId, ownerId, content))
                        .willThrow(new CompensationNotFoundException(compensationId));
            }

            @DisplayName("404 Not Found를 응답한다")
            @Test
            void it_response_404() throws Exception {
                mvc.perform(
                        post(String.format("/compensations/%s/penalties", compensationId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(request))
                ).andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message")
                                .value(String.format("%s에 해당하는 배상 정보를 찾을 수 없습니다.", compensationId))
                );
            }
        }

        @DisplayName("유효하지 않은 페널티 발급 요청 객체가 주어지면")
        @Nested
        class Context_with_invalid_data{
            @DisplayName("400 Bad Request를 응답한다")
            @ParameterizedTest
            @MethodSource("createInvalidRequestDatas")
            void is_response_400(PenaltyIssueController.Request invalidRequest) throws Exception {
                mvc.perform(
                        post(String.format("/compensations/%s/penalties", compensationId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(invalidRequest))
                ).andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors").isNotEmpty()
                );
            }

            private static Stream<Arguments> createInvalidRequestDatas() {
                return Stream.of(
                        Arguments.of(new PenaltyIssueController.Request(0L, "content")),
                        Arguments.of(new PenaltyIssueController.Request(1L, "")),
                        Arguments.of(new PenaltyIssueController.Request(1L, " ")),
                        Arguments.of( new PenaltyIssueController.Request(1L, "a".repeat(501)))
                );
            }
        }
    }
}
