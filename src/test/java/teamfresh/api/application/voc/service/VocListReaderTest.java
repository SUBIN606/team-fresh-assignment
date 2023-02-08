package teamfresh.api.application.voc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VocListReaderTest {

    @InjectMocks
    private VocListReader vocListReader;

    @Mock
    private VocRepository repository;

    @DisplayName("read 메서드")
    @Nested
    class Describe_read {

        @BeforeEach
        void setUp() {
            given(repository.findAllWithFetch())
                    .willReturn(createVocList());
        }

        @DisplayName("모든 VOC 목록을 조회 후 반환한다")
        @Test
        void it_returns_all_vocs() {
            List<Voc> vocList = vocListReader.read();

            verify(repository, times(1)).findAllWithFetch();
            assertThat(vocList).isNotEmpty();
        }
    }

    private List<Voc> createVocList() {
        return List.of(
                Voc.of(
                        1L,
                        "voc content 1",
                        Blame.of(1L, BlameTarget.CARRIER, "blaem cause 1"),
                        Compensation.of(1L, 10000),
                        8L,
                        99L
                ),
                Voc.of(
                        2L,
                        "voc content 2",
                        Blame.of(2L, BlameTarget.CARRIER, "blaem cause 2"),
                        Compensation.of(2L, 10000),
                        4L,
                        99L
                )
        );
    }
}
