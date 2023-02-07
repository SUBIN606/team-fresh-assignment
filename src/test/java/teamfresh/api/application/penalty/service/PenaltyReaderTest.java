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
import teamfresh.api.application.penalty.domain.PenaltyRepository;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PenaltyReaderTest {

    @InjectMocks
    private PenaltyReader penaltyReader;

    @Mock
    private PenaltyRepository repository;

    @DisplayName("read 메서드")
    @Nested
    class Describe_read {

        Long penaltyId = 1L;

        @DisplayName("찾을 수 있는 id가 주어지면")
        @Nested
        class Context_with_exist_id {
            @BeforeEach
            void setUp() {
                given(repository.findById(any()))
                        .willReturn(Optional.of(Penalty.of(penaltyId, null, 3L)));
            }

            @DisplayName("조회한 페널티를 반환한다")
            @Test
            void it_return_penalty() {
                Penalty penalty = penaltyReader.read(penaltyId);

                assertThat(penalty.getId()).isEqualTo(penaltyId);
            }
        }

        @DisplayName("찾을 수 없는 id가 주어지면")
        @Nested
        class Context_with_not_exist_id {
            @BeforeEach
            void setUp() {
                given(repository.findById(any()))
                        .willReturn(Optional.empty());
            }

            @DisplayName("PenaltyNotFoundException을 던진다")
            @Test
            void it_throws_penalty_not_found_exception() {
                assertThrows(PenaltyNotFoundException.class,
                        () -> penaltyReader.read(penaltyId));
            }
        }
    }
}
