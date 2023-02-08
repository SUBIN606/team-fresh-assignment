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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompensationListReaderTest {

    @InjectMocks
    private CompensationListReader compensationListReader;

    @Mock
    private CompensationRepository repository;

    @DisplayName("read 메서드")
    @Nested
    class Describe_read {

        @BeforeEach
        void setUp() {
            given(repository.findAllWithFetch())
                    .willReturn(createCompensationList());
        }

        @DisplayName("배상목록을 조회 후 반환한다")
        @Test
        void it_returns_compensation_list() {
            List<Compensation> compensationList = compensationListReader.read();

            verify(repository, times(1)).findAllWithFetch();
            assertThat(compensationList).isNotEmpty();
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
