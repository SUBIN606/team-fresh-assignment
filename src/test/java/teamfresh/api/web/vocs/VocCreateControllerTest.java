package teamfresh.api.web.vocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.service.VocCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VocCreateControllerTest {

    @InjectMocks
    private VocCreateController vocCreateController;

    @Mock
    private VocCreator vocCreator;

    @DisplayName("handleVocCreate 메서드")
    @Nested
    class Describe_handle_voc_create {

        Long vocId = 8L;
        String content = "voc content";
        BlameTarget target = BlameTarget.CARRIER;
        Long targetCompanyId = 12L;
        String cause = "blame cause";
        Long customerManagerId = 33L;
        Long createdBy = 1L;

        @BeforeEach
        void setUp() {
            given(vocCreator.create(any()))
                    .willReturn(Voc.of(vocId, content, createdBy));
        }

        @DisplayName("VOC 생성 요청 데이터가 주어지면")
        @Nested
        class Context_with_voc_create_request_data {
            @DisplayName("생성된 VOC의 id를 반환한다")
            @Test
            void it_return_voc_id() {
                //given
                VocCreateController.Request request
                        = new VocCreateController.Request(
                        content,
                        target,
                        targetCompanyId,
                        cause,
                        customerManagerId,
                        createdBy
                );

                //when
                VocCreateController.Response response = vocCreateController.handleVocCreate(request);

                //then
                assertThat(response.getId()).isEqualTo(vocId);
            }
        }
    }
}
