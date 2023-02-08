package teamfresh.api.web.compensations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.service.CompensationListReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CompensationListControllerTest {

    @InjectMocks
    private CompensationListController compensationListController;

    @Mock
    private CompensationListReader compensationListReader;

    @DisplayName("handleReadCompensationList 메서드")
    @Nested
    class Describe_handle_read_compensation_list {

        List<Compensation> compensationList = createCompensationList();

        @BeforeEach
        void setUp() {
            given(compensationListReader.read())
                    .willReturn(compensationList);
        }

        @DisplayName("배상 목록을 반환한다")
        @Test
        void it_returns_compensation_list() {
            CompensationListController.Response response
                    = compensationListController.handleReadCompensationList();

            assertThat(compensationList.size()).isEqualTo(response.getCompensations().size());
        }
    }

    private List<Compensation> createCompensationList() {
        return List.of(
                Compensation.of(1L, 10000),
                Compensation.of(2L, 11000),
                Compensation.of(3L, 30000),
                Compensation.of(4L, 1000)
        );
    }
}
