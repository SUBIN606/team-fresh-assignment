package teamfresh.api.web.penalties.read;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.penalty.service.PenaltyReadUpdater;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PenaltyReadUpdateController.class)
public class PenaltyReadUpdateControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PenaltyReadUpdater penaltyReadUpdater;

    @DisplayName("PATCH /penalties/{id}/read")
    @Nested
    class Describe_handle_read_penalty {

        Long id = 1L;

        @DisplayName("204 No Content를 응답한다")
        @Test
        void it_update_read_true() throws Exception {
            mvc.perform(
                    patch(String.format("/penalties/%s/read", id))
            ).andExpectAll(
                    status().isNoContent()
            );
        }
    }
}
