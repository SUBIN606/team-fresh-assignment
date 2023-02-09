package teamfresh.api.web.vocs.compensations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.exception.CompensationAlreadyExistException;
import teamfresh.api.application.compensation.service.CompensationCreator;
import teamfresh.api.application.voc.exception.VocNotFoundException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static teamfresh.api.web.MvcTestHelpers.toJSON;

@WebMvcTest(CompensationsCreateController.class)
public class CompensationsCreateControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompensationCreator compensationCreator;

    @DisplayName("POST /vocs/{vocId}/compensations")
    @Nested
    class Describe_post_compensation {

        Long vocId = 3L;
        int amount = 28000;
        Long compensationId = 1L;
        CompensationsCreateController.Request request
                = new CompensationsCreateController.Request(amount);

        @DisplayName("찾을 수 있는 vocId가 주어지고, 이미 등록된 배상정보가 없다면")
        @Nested
        class Context_with_exist_voc_id {

            @BeforeEach
            void setUp() {
                given(compensationCreator.create(vocId, amount))
                        .willReturn(Compensation.of(compensationId, amount));
            }

            @DisplayName("201 Created 상태코드와 함께 등록된 배상 정보의 id를 반환한다")
            @Test
            void it_response_compensation_id_with_201_status() throws Exception {
                mvc.perform(
                        post(String.format("/vocs/%s/compensations", vocId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(request))
                ).andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").isNumber(),
                        jsonPath("$.id").value(compensationId)
                );
            }
        }

        @DisplayName("vocId에 해당하는 VOC를 찾지 못하면")
        @Nested
        class Context_with_not_found_VOC{
            @BeforeEach
            void setUp() {
                given(compensationCreator.create(vocId, amount))
                        .willThrow(new VocNotFoundException(vocId));
            }

            @DisplayName("404 Not Found를 응답한다")
            @Test
            void it_response_404() throws Exception {
                mvc.perform(
                        post(String.format("/vocs/%s/compensations", vocId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(request))
                ).andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message")
                                .value(String.format("%s에 해당하는 Voc를 찾을 수 없습니다.", vocId))
                );
            }
        }

        @DisplayName("이미 VOC에 등록된 배상 정보가 존재하면")
        @Nested
        class Context_with_already_exist_compensation {
            @BeforeEach
            void setUp() {
                given(compensationCreator.create(vocId, amount))
                        .willThrow(new CompensationAlreadyExistException());
            }

            @DisplayName("400 Bad Request를 응답한다")
            @Test
            void it_response_400() throws Exception {
                mvc.perform(
                        post(String.format("/vocs/%s/compensations", vocId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(request))
                ).andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("이미 존재하는 배상정보가 있습니다.")
                );
            }
        }

        @DisplayName("0 혹은 0보다 작은 배상 금액이 주어지면")
        @Nested
        class Context_with_negative_amount{
            @DisplayName("400 Bad Request를 응답한다")
            @ParameterizedTest
            @ValueSource(ints = {0, -1})
            void it_response_400(int amount) throws Exception {
                CompensationsCreateController.Request invalidRequest
                        = new CompensationsCreateController.Request(amount);

                mvc.perform(
                        post(String.format("/vocs/%s/compensations", vocId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJSON(invalidRequest))
                ).andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0].field").value("amount"),
                        jsonPath("$.errors[0].message").value(
                                "배상 금액은 0보다 큰 숫자를 입력해야 합니다."
                        )
                );
            }
        }
    }
}
