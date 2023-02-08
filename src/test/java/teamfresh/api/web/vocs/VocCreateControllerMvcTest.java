package teamfresh.api.web.vocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.service.VocCreator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamfresh.api.web.MvcTestHelpers.toJSON;

@WebMvcTest(VocCreateController.class)
public class VocCreateControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VocCreator vocCreator;

    @DisplayName("POST /vocs")
    @Nested
    class Describe_post_vocs {

        Long vocId = 8L;
        String content = "voc content";
        BlameTarget target = BlameTarget.CARRIER;
        Long targetCompanyId = 12L;
        String cause = "blame cause";
        Long customerManagerId = 33L;
        Long createdBy = 1L;

        @BeforeEach
        void setUp() {
            given(vocCreator.create(any()))
                    .willReturn(Voc.of(vocId, content, createdBy));
        }

        @DisplayName("201 Created 상태코드와 함께 생성된 VOC id를 응답한다")
        @Test
        void it_response_voc_id_with_201_status() throws Exception {
            VocCreateController.Request request = new VocCreateController.Request(
                    content, target, targetCompanyId, cause, customerManagerId, createdBy
            );

            mvc.perform(
                    post("/vocs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJSON(request))
            ).andExpectAll(
                    status().isCreated(),
                    jsonPath("$.id").isNumber(),
                    jsonPath("$.id").value(vocId)
            );
        }
    }
}
