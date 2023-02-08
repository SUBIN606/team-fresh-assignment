package teamfresh.api.web.compensations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.service.CompensationListReader;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompensationListController.class)
public class CompensationListControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompensationListReader compensationListReader;

    @DisplayName("GET /compensations")
    @Nested
    class Describe_handle_read_compensation_list {

        List<Compensation> compensationList = createCompensationList();

        @BeforeEach
        void setUp() {
            given(compensationListReader.read())
                    .willReturn(compensationList);
        }

        @DisplayName("배상 목록을 200 Ok 상태코드와 함께 반환한다")
        @Test
        void it_response_compensation_list_with_200_status() throws Exception {
            mvc.perform(
                    get("/compensations")
            ).andExpectAll(
                    status().isOk(),
                    jsonPath("$.compensations").isArray(),
                    jsonPath("$.compensations[0].voc").exists()
            );
        }
    }

    private List<Compensation> createCompensationList() {
        return List.of(
                Compensation.of(1L, 10000),
                Compensation.of(2L, 11000),
                Compensation.of(3L, 30000),
                Compensation.of(4L, 1000)
        );
    }
}
