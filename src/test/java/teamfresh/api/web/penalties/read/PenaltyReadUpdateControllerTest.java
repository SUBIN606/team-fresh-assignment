package teamfresh.api.web.penalties.read;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;
import teamfresh.api.application.penalty.service.PenaltyReadUpdater;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PenaltyReadUpdateControllerTest {

    @InjectMocks
    private PenaltyReadUpdateController controller;

    @Mock
    private PenaltyReadUpdater penaltyReadUpdater;

    @DisplayName("handleReadPenalty 메서드")
    @Nested
    class Describe_handle_read_penalty {

        Long penaltyId = 1L;

        @DisplayName("찾을 수 있는 페널티 id가 주어지면")
        @Nested
        class Context_with_exist_penalty_id {
            @DisplayName("페널티를 읽음 처리 한다")
            @Test
            void it_change_read_to_true() {
                controller.handlePenaltyRead(penaltyId);

                verify(penaltyReadUpdater, times(1))
                        .update(penaltyId);
            }
        }

        @DisplayName("주어진 페널티 id에 해당하는 페널티를 찾을 수 없으면")
        @Nested
        class Context_with_not_exist_penalty_id {
            @BeforeEach
            void setUp() {
                doThrow(new PenaltyNotFoundException(penaltyId))
                        .when(penaltyReadUpdater).update(penaltyId);
            }

            @DisplayName("PenaltyNotFoundException을 던진다")
            @Test
            void it_throws_penalty_not_found_exception() {
                assertThrows(PenaltyNotFoundException.class,
                        () -> controller.handlePenaltyRead(penaltyId));
            }
        }
    }
}
