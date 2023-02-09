package teamfresh.api.web.penalties.read;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;
import teamfresh.api.application.penalty.service.PenaltyReadUpdater;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        @DisplayName("id에 해당하는 페널티를 찾지 못하면")
        @Nested
        class Context_with_not_found_penalty {
            @BeforeEach
            void setUp() {
                doThrow(new PenaltyNotFoundException(id))
                        .when(penaltyReadUpdater).update(id);
            }

            @DisplayName("404 Not Found를 응답한다")
            @Test
            void it_response_404() throws Exception {
                mvc.perform(
                        patch(String.format("/penalties/%s/read", id))
                ).andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message")
                                .value(String.format("%s에 해당하는 페널티 정보를 찾을 수 없습니다.", id))
                );
            }
        }
    }
}
