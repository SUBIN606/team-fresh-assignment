package teamfresh.api.web.vocs.compensations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.service.CompensationCreator;
import teamfresh.api.application.voc.exception.VocNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CompensationsCreateControllerTest {

    @InjectMocks
    private CompensationsCreateController compensationsCreateController;

    @Mock
    private CompensationCreator compensationCreator;

    @DisplayName("handleCompensationCreate 메서드")
    @Nested
    class Describe_handle_compensation_create {

        Long vocId = 3L;
        int amount = 28000;
        Long compensationId = 1L;
        CompensationsCreateController.Request request
                = new CompensationsCreateController.Request(amount);

        @BeforeEach
        void setUp() {
            given(compensationCreator.create(vocId, amount))
                    .willReturn(Compensation.of(compensationId, amount));
        }

        @DisplayName("배상을 등록할 VOC 건의 id와 배상 금액이 주어지면")
        @Nested
        class Context_with_voc_id_and_amount {
            @DisplayName("등록된 배상 정보의 id를 반환한다")
            @Test
            void it_return_compensation_id() {
                CompensationsCreateController.Response response
                        = compensationsCreateController.handleCompensationCreate(vocId, request);

                assertThat(response.getId()).isEqualTo(compensationId);
            }
        }

        @DisplayName("주어진 vocId에 해당하는 VOC를 찾지 못한 경우")
        @Nested
        class Context_with_not_exist_voc_id {

            @BeforeEach
            void setUp() {
                given(compensationCreator.create(vocId, amount))
                        .willThrow(new VocNotFoundException(vocId));
            }

            @DisplayName("VocNotFoundException을 던진다")
            @Test
            void it_throws_voc_not_found_exception() {
                assertThrows(
                        VocNotFoundException.class,
                        () -> compensationsCreateController
                                .handleCompensationCreate(vocId, request)
                );
            }
        }
    }
}
