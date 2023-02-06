package teamfresh.api.application.voc.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import teamfresh.api.application.compensation.domain.Compensation;

import static org.assertj.core.api.Assertions.assertThat;

class VocTest {

    @DisplayName("registerCompensation 메서드는")
    @Nested
    class Describe_register_compensation {
        @DisplayName("배상 정보를 접수한다")
        @Test
        void it_register_compensation() {
            //given
            Compensation compensation = Compensation.of(10000);
            Voc voc = Voc.of("content", 1L);

            //when
            voc.registerCompensation(compensation);

            //then
            assertThat(voc.getCompensation()).isNotNull();
            assertThat(voc.getCompensation()).isEqualTo(compensation);
        }
    }
}
