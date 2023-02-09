package teamfresh.api.application.voc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VocCreatorIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VocRepository vocRepository;

    private VocCreator vocCreator;

    @BeforeEach
    void setUp() {
        this.vocCreator = new VocCreator(vocRepository);
    }

    Long createdBy = 1L;
    String content = "배송 물건 파손";
    BlameTarget target = BlameTarget.CARRIER;
    Long targetCompanyId = 12L;
    String cause = "배송 중 박스가 박스 훼손으로 인한 물건 파손";
    Long customerManagerId = 9L;

    @DisplayName("create 메서드")
    @Nested
    class Describe_create {
        @DisplayName("Voc 등록 시 Blame도 함께 저장된다")
        @Test
        void test() {
            VocCreator.Command command = new VocCreator.Command(
                    content, target, targetCompanyId, cause, customerManagerId, createdBy
            );

            Voc voc = vocCreator.create(command);

            assertThat(voc.getBlame()).isNotNull();
            assertThat(voc.getBlame().getId()).isNotNull();
            assertThat(entityManager.find(Blame.class, voc.getBlame().getId()))
                    .isNotNull();
        }
    }
}
