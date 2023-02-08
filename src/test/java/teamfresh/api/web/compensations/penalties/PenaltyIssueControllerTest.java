package teamfresh.api.web.compensations.penalties;

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
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.service.PenaltyIssuer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PenaltyIssueControllerTest {

    @InjectMocks
    private PenaltyIssueController penaltyIssueController;

    @Mock
    private PenaltyIssuer penaltyIssuer;

    @DisplayName("handleIssuePenalty 메서드")
    @Nested
    class Describe_handle_issue_penalty {

        Long compensationId = 4L;
        Long ownerId = 11L;
        Long penaltyId = 8L;
        String content = "페널티 내용";

        @DisplayName("배상정보 id와 페널티 대상 id가 주어지면")
        @Nested
        class Context_with_compensation_id_and_owner_id {
            @BeforeEach
            void setUp() {
                given(penaltyIssuer.issue(compensationId, ownerId, content))
                        .willReturn(
                                Penalty.of(
                                        penaltyId,
                                        Compensation.of(compensationId, 100000),
                                        ownerId,
                                        content
                                )
                        );
            }

            @DisplayName("발급된 페널티 id를 반환한다")
            @Test
            void it_returns_issued_penalty_id() {
                PenaltyIssueController.Request request
                    = new PenaltyIssueController.Request(ownerId, content);

                PenaltyIssueController.Response response
                        = penaltyIssueController.handleIssuePenalty(
                                compensationId, request
                );

                assertThat(response.getId()).isEqualTo(penaltyId);
            }
        }

        @DisplayName("찾을 수 없는 배상정보 id가 주어지면")
        @Nested
        class Context_with_not_exist_compensation_id {
            @BeforeEach
            void setUp() {
                given(penaltyIssuer.issue(compensationId, ownerId, content))
                        .willThrow(new CompensationNotFoundException(compensationId));
            }

            @DisplayName("CompensationNotFoundException을 던진다")
            @Test
            void it_throws_compensation_not_found_exception() {
                PenaltyIssueController.Request request
                        = new PenaltyIssueController.Request(ownerId, content);

                assertThrows(CompensationNotFoundException.class,
                        () -> penaltyIssueController.handleIssuePenalty(
                                compensationId, request
                        )
                );
            }
        }
    }

}
