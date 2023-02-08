package teamfresh.api.web.vocs;

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
import teamfresh.api.application.voc.service.VocListReader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VocListControllerTest {

    @InjectMocks
    private VocListController vocListController;

    @Mock
    private VocListReader vocListReader;

    @DisplayName("handleReadVocList 메서드")
    @Nested
    class Describe_handle_read_voc_list {

        @BeforeEach
        void setUp() {
            given(vocListReader.read())
                    .willReturn(createVodList());
        }

        @DisplayName("VOC 목록읇 반환한다")
        @Test
        void it_returns_voc_list() {
            VocListController.Response response = vocListController.handleReadVocList();

            assertThat(response.getVocs()).isInstanceOf(List.class);
            assertThat(response.getVocs().get(0))
                    .isInstanceOf(VocListController.Response.VocDto.class);
        }
    }

    private List<Voc> createVodList() {
        return List.of(
                Voc.of(
                        1L,
                        "voc content 1",
                        Blame.of(1L, BlameTarget.CARRIER, "blaem cause 1"),
                        Compensation.of(1L, 10000),
                        8L,
                        99L
                )
        );
    }
}
