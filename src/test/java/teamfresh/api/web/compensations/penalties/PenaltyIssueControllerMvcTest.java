package teamfresh.api.web.compensations.penalties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.service.PenaltyIssuer;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

        @DisplayName("배상정보 id와 페널티 대상 id가 주어지면")
        @Nested
        class Context_with_compensation_id_and_owner_id {
            @BeforeEach
            void setUp() {
                given(penaltyIssuer.issue(compensationId, ownerId))
                        .willReturn(Penalty.of(penaltyId, Compensation.of(compensationId, 100000), ownerId));
            }

            @DisplayName("201 Created 상태코드와 함께 발급된 페널티 id를 응답한다")
            @Test
            void it_returns_issued_penalty_id() throws Exception {
                PenaltyIssueController.Request request
                        = new PenaltyIssueController.Request(ownerId);

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
    }
}
