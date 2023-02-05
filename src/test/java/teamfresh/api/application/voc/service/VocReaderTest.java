package teamfresh.api.application.voc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;
import teamfresh.api.application.voc.exception.VocNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VocReaderTest {

    @InjectMocks
    private VocReader vocReader;

    @Mock
    private VocRepository repository;

    @DisplayName("read 메서드")
    @Nested
    class Describe_of_read {

        Long id = 72L;
        String content = "voc content";
        Long createdBy = 11L;

        @BeforeEach
        void setUp() {
            given(repository.findById(id))
                    .willReturn(Optional.of(Voc.of(id, content, createdBy)));
        }

        @DisplayName("주어진 VOC id로 VOC를 찾을 수 있으면")
        @Nested
        class Context_with_exist_id {
            @DisplayName("VOC를 반환한다")
            @Test
            void It_will_return_voc() {
                Voc voc = vocReader.read(id);

                assertThat(voc).isNotNull();
                assertThat(voc.getId()).isEqualTo(id);
            }
        }

        @DisplayName("찾을 수 없는 VOC id가 주어지면")
        @Nested
        class Context_with_not_exist_id {

            @BeforeEach
            void setUp() {
                given(repository.findById(id))
                        .willReturn(Optional.empty());
            }

            @DisplayName("VocNotFoundException을 던진다")
            @Test
            void it_throws_voc_not_found_exception() {
                assertThrows(VocNotFoundException.class, () -> vocReader.read(id));
            }
        }
    }
}
