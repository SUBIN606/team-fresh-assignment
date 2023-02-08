package teamfresh.api.web.vocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.service.VocListReader;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VocListController.class)
public class VocListControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VocListReader vocListReader;

    @DisplayName("GET /vocs")
    @Nested
    class Describe_handle_read_voc_list {

        @BeforeEach
        void setUp() {
            given(vocListReader.read())
                    .willReturn(createVodList());
        }

        @DisplayName("200 Ok 상태코드와 함게 VOD 목록을 반환한다")
        @Test
        void it_response_vocs_with_200_status() throws Exception {
            mvc.perform(
                    get("/vocs")
            ).andDo(print()).andExpectAll(
                    status().isOk(),
                    jsonPath("$.vocs").isArray(),
                    jsonPath("$.vocs[0].blame").exists(),
                    jsonPath("$.vocs[0].compensation").exists(),
                    jsonPath("$.vocs[0].penalty").exists()
            );
        }
    }

    private List<Voc> createVodList() {
        return List.of(
                Voc.of(
                        1L,
                        "voc content 1",
                        Blame.of(1L, BlameTarget.CARRIER, "blaem cause 1"),
                        Compensation.of(1L, 10000),
                        8L,
                        99L
                )
        );
    }
}
