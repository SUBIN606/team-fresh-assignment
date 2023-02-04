package teamfresh.api.application.voc.blame.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameRepository;
import teamfresh.api.application.voc.blame.domain.BlameTarget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlameCreatorTest {

    @InjectMocks
    private BlameCreator blameCreator;

    @Mock
    private BlameRepository repository;

    @DisplayName("create 메서드")
    @Nested
    class Describe_create {

        @DisplayName("귀책 정보가 주어지면")
        @Nested
        class Context_with_blame_data {
            BlameTarget target = BlameTarget.CARRIER;
            String cause = "배송 중 박스가 박스 훼손으로 인한 물건 파손";
            Blame blame = Blame.of(1L, target, cause);

            @DisplayName("Blame 생성 후 반환한다")
            @Test
            void it_create_and_return_blame() {
                given(repository.save(any())).willReturn(blame);

                Blame savedBlame = blameCreator.create(target, cause);

                assertThat(savedBlame.getId()).isNotNull();
                assertThat(savedBlame.getTarget()).isEqualTo(target);
                assertThat(savedBlame.getCause()).isEqualTo(cause);
            }
        }
    }
}
