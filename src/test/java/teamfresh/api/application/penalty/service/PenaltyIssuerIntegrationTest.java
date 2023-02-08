package teamfresh.api.application.penalty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.exception.CompensationNotFoundException;
import teamfresh.api.application.compensation.service.CompensationReader;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.domain.PenaltyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PenaltyIssuerIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PenaltyRepository repository;

    @SpyBean
    private CompensationReader compensationReader;

    private PenaltyIssuer penaltyIssuer;

    @BeforeEach
    void setUp() {
        this.penaltyIssuer = new PenaltyIssuer(compensationReader, repository);
    }

    @DisplayName("issue 메서드")
    @Nested
    class Describe_issue {

        Long compensationId;
        Long owner = 8L;
        String content = "페널티 내용";

        @BeforeEach
        void setUp() {
            this.compensationId = entityManager.persist(
                    Compensation.of(43000)
            ).getId();
        }

        @DisplayName("페널티 정보가 배상정보 id와 함께 저장된다")
        @Test
        void it_will_save_penalty() {
            Penalty penalty = penaltyIssuer.issue(compensationId, owner, content);

            assertThat(penalty).isNotNull();
            assertThat(penalty.getCompensation().getId()).isEqualTo(compensationId);
            assertThat(
                    entityManager.find(Penalty.class, penalty.getId())
            ).isEqualTo(penalty);
        }

        @DisplayName("주어진 배상정보 id로 배상정보를 찾을 수 없으면")
        @Nested
        class Context_with_not_exist_compensation_id {

            @BeforeEach
            void setUp() {
                Compensation compensation = entityManager.find(Compensation.class, compensationId);
                if (compensation != null) {
                    entityManager.remove(compensation);
                }
            }

            @DisplayName("CompensationNotFoundException을 던진다")
            @Test
            void it_throws_compensation_not_found_exception() {
                assertThrows(CompensationNotFoundException.class,
                        () -> penaltyIssuer.issue(compensationId, owner, content));
            }
        }
    }
}
