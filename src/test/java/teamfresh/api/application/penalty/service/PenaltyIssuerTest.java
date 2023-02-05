package teamfresh.api.application.penalty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.exception.CompensationNotFoundException;
import teamfresh.api.application.compensation.service.CompensationReader;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.domain.PenaltyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PenaltyIssuerTest {

    @InjectMocks
    private PenaltyIssuer penaltyIssuer;

    @Mock
    private CompensationReader compensationReader;

    @Mock
    private PenaltyRepository repository;

    @DisplayName("issue 메서드")
    @Nested
    class Describe_issue {

        Long compensationId = 1L;
        Compensation compensation = Compensation.of(compensationId, 32000);
        Long owner = 17L;


        @DisplayName("배상정보 id와 페널티 대상 id가 주어지면")
        @Nested
        class Context_with_valid_data {

            @BeforeEach
            void setUp() {
                given(compensationReader.read(compensationId))
                        .willReturn(compensation);
                given(repository.save(any()))
                        .willReturn(Penalty.of(1L, compensation, owner));
            }

            @DisplayName("페널티를 생성 후 반환한다")
            @Test
            void it_returns_penalty() {
                Penalty penalty = penaltyIssuer.issue(compensationId, owner);

                assertThat(penalty).isNotNull();
                assertThat(penalty.getCompensation().getId()).isEqualTo(compensationId);
            }
        }

        @DisplayName("주어진 배상정보 id로 배상정보를 찾을 수 없으면")
        @Nested
        class Context_with_not_exist_compensation_id {

            @BeforeEach
            void setUp() {
                given(compensationReader.read(compensationId))
                        .willThrow(new CompensationNotFoundException(compensationId));
            }

            @DisplayName("CompensationNotFoundException을 던진다")
            @Test
            void it_throws_compensation_not_found_exception() {
                assertThrows(CompensationNotFoundException.class,
                        () -> penaltyIssuer.issue(compensationId, owner));
            }
        }
    }
}
