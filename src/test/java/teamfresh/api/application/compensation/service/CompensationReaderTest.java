package teamfresh.api.application.compensation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.domain.CompensationRepository;
import teamfresh.api.application.compensation.exception.CompensationNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CompensationReaderTest {

    @InjectMocks
    private CompensationReader compensationReader;

    @Mock
    private CompensationRepository repository;

    @DisplayName("read 메서드")
    @Nested
    class Describe_of_read {

        Long id = 1L;
        int amount = 30000;

        @DisplayName("주어진 id로 배상 정보를 찾을 수 있으면")
        @Nested
        class Context_with_exist_id {

            @BeforeEach
            void setUp() {
                given(repository.findById(id))
                        .willReturn(Optional.of(Compensation.of(id, amount)));
            }

            @DisplayName("배상 정보를 반환한다")
            @Test
            void it_returns_compensation() {
                Compensation compensation = compensationReader.read(id);

                assertThat(compensation).isNotNull();
                assertThat(compensation.getId()).isEqualTo(id);
                assertThat(compensation.getAmount()).isEqualTo(amount);
            }
        }

        @DisplayName("주어진 id로 배상 정보를 찾지 못하면")
        @Nested
        class Context_with_not_exist_id {

            @BeforeEach
            void setUp() {
                given(repository.findById(id))
                        .willReturn(Optional.empty());
            }

            @DisplayName("CompensationNotFoundException을 던진다")
            @Test
            void it_throws_compensation_not_found_exception() {
                assertThrows(CompensationNotFoundException.class,
                        () -> compensationReader.read(id));
            }
        }
    }

}
