package teamfresh.api.web.vocs.compensations;

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
import teamfresh.api.application.compensation.service.CompensationCreator;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

        @BeforeEach
        void setUp() {
            given(compensationCreator.create(vocId, amount))
                    .willReturn(Compensation.of(compensationId, amount));
        }

        @DisplayName("201 Created 상태코드와 함께 등록된 배상 정보의 id를 반환한다")
        @Test
        void it_response_compensation_id_with_201_status() throws Exception {
            CompensationsCreateController.Request request
                    = new CompensationsCreateController.Request(amount);

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
}
