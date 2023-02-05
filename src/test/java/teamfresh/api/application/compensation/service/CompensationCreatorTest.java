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
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.exception.VocNotFoundException;
import teamfresh.api.application.voc.service.VocReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CompensationCreatorTest {

    @InjectMocks
    private CompensationCreator compensationCreator;

    @Mock
    private VocReader vocReader;

    @Mock
    private CompensationRepository repository;

    @DisplayName("create 메서드")
    @Nested
    class Describe_create {

        Voc voc = createVoc();
        Long compensationId = 6L;
        int amount = 100000;

        @DisplayName("VOC 건에 대한 배상 정보가 주어지면")
        @Nested
        class Context_with_compensation_data {

            @BeforeEach
            void setUp() {
                given(vocReader.read(voc.getId()))
                        .willReturn(voc);
                given(repository.save(any()))
                        .willReturn(Compensation.of(compensationId, amount));
            }

            @DisplayName("배상 정보를 저장하고, VOC의 compensationId를 갱신한 후 반환한다")
            @Test
            void it_will_create_compensation() {
                Compensation compensation = compensationCreator.create(voc.getId(), amount);

                assertThat(compensation).isNotNull();
                assertThat(voc.getCompensation().getId()).isEqualTo(compensationId);
            }
        }

        @DisplayName("찾을 수 없는 VOC id가 주어지면")
        @Nested
        class Context_with_not_exist_voc_id {

            @BeforeEach
            void setUp() {
                given(vocReader.read(voc.getId()))
                        .willThrow(new VocNotFoundException(voc.getId()));
            }

            @DisplayName("VocNotFoundException을 던진다")
            @Test
            void it_throws_voc_not_found_exception() {
                assertThrows(VocNotFoundException.class,
                        () -> compensationCreator.create(voc.getId(), amount));
            }
        }
    }

    private Voc createVoc() {
        return Voc.of(
                "배송 중 물건 파손",
                Blame.of(
                        1L,
                        BlameTarget.CARRIER,  "배송 중 박스가 박스 훼손으로 인한 물건 파손"
                ),
                5L,
                1L
        );
    }
}
