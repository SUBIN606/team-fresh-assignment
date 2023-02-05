package teamfresh.api.application.voc.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.service.BlameCreator;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VocCreatorTest {

    @InjectMocks
    private VocCreator vocCreator;

    @Mock
    private BlameCreator blameCreator;

    @Mock
    private VocRepository vocRepository;

    @DisplayName("create 메서드는")
    @Nested
    class Describe_create {

        Long createdBy = 1L;
        String content = "배송 물건 파손";
        BlameTarget target = BlameTarget.CARRIER;
        String cause = "배송 중 박스가 박스 훼손으로 인한 물건 파손";
        Long customerManagerId = 9L;

        Blame blame = Blame.of(1L, target, cause);
        Voc voc = Voc.of(1L, content, blame, customerManagerId, createdBy);

        @DisplayName("VOC를 생성 후 반환한다")
        @Test
        void it_create_voc() {
            given(blameCreator.create(target, cause)).willReturn(blame);
            given(vocRepository.save(any())).willReturn(voc);

            Voc savedVoc = vocCreator.create(
                    new VocCreator.Command(
                            content, target, cause, customerManagerId, createdBy
                    )
            );

            assertAll(()->{
                assertThat(savedVoc).isNotNull();
                assertThat(savedVoc.getId()).isNotNull();
                assertThat(savedVoc.getBlame()).isNotNull();
                assertThat(savedVoc.getBlame().getId()).isNotNull();
            });
        }
    }
}
