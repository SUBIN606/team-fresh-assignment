package teamfresh.api.application.penalty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PenaltyReadUpdaterTest {

    @InjectMocks
    private PenaltyReadUpdater penaltyReadUpdater;

    @Mock
    private PenaltyReader penaltyReader;

    @DisplayName("update 메서드")
    @Nested
    class Describe_update {

        Long id = 1L;

        @DisplayName("찾을 수 있는 페널티 id가 주어지면")
        @Nested
        class Context_with_exist_if {

            Penalty penalty = Penalty.of(id, null, 2L);

            @BeforeEach
            void setUp() {
                given(penaltyReader.read(id))
                        .willReturn(penalty);
            }

            @DisplayName("read 상태를 true로 갱신한다")
            @Test
            void it_change_read_to_true() {
                penaltyReadUpdater.update(id);

                assertThat(penalty.getRead()).isTrue();
            }
        }

        @DisplayName("찾을 수 없는 페널티 id가 주어지면")
        @Nested
        class Context_with_not_exist_id {
            @BeforeEach
            void setUp() {
                given(penaltyReader.read(id))
                        .willThrow(new PenaltyNotFoundException(id));
            }

            @DisplayName("PenaltyNotFoundException을 던진다")
            @Test
            void it_throws_penalty_not_found_exception() {
                assertThrows(PenaltyNotFoundException.class,
                        () -> penaltyReadUpdater.update(id));
            }
        }
    }

}
