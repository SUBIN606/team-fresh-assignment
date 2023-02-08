package teamfresh.api.application.penalty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.domain.PenaltyRepository;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PenaltyReadUpdaterIntegrationTest {

    @Autowired
    private PenaltyRepository repository;

    @SpyBean
    private PenaltyReader penaltyReader;

    private PenaltyReadUpdater penaltyReadUpdater;

    @BeforeEach
    void setUp() {
        this.penaltyReadUpdater = new PenaltyReadUpdater(penaltyReader);
    }

    @DisplayName("update 메서드")
    @Nested
    class Describe_update {

        Long penaltyId;

        @BeforeEach
        void setUp() {
            penaltyId =
                    repository.save(
                            Penalty.of(null, 2L)
                    ).getId();
        }

        @DisplayName("찾을 수 있는 페널티 id가 주어지면")
        @Nested
        class Context_with_exist_id {
            @DisplayName("read 상태를 true로 갱신한다")
            @Test
            void it_update_read_true() {
                penaltyReadUpdater.update(penaltyId);

                assertThat(
                        repository.findById(penaltyId).get().getRead()
                ).isTrue();
            }
        }

        @DisplayName("찾을 수 없는 페널티 id가 주어지면")
        @Nested
        class Context_with_not_exist_id {
            @BeforeEach
            void setUp() {
                if(repository.findById(penaltyId).isPresent()) {
                    repository.deleteById(penaltyId);
                }
            }

            @DisplayName("PenaltyNotFoundException을 던진다")
            @Test
            void it_throws_penalty_not_found_exception() {
                assertThrows(PenaltyNotFoundException.class,
                        () -> penaltyReadUpdater.update(penaltyId));
            }
        }
    }
}
