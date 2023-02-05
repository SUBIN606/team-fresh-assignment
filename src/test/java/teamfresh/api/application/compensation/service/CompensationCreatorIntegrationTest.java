package teamfresh.api.application.compensation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.SpyBean;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.domain.CompensationRepository;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.exception.VocNotFoundException;
import teamfresh.api.application.voc.service.VocReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CompensationCreatorIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompensationRepository compensationRepository;

    @SpyBean
    private VocReader vocReader;

    private CompensationCreator compensationCreator;

    @BeforeEach
    void setUp() {
        this.compensationCreator
                = new CompensationCreator(vocReader, compensationRepository);
    }

    @DisplayName("create 메서드")
    @Nested
    class Describe_create {

        private Long vocId;

        @BeforeEach
        void setUp() {
            vocId = entityManager.persist(
                    Voc.of("vocContent", 1L)
            ).getId();
        }

        @DisplayName("배상정보가 저장된 후 해당 VOC 건에 등록된다")
        @Test
        void it_will_update_voc() {
            Compensation compensation = compensationCreator.create(vocId, 100000);
            entityManager.flush();

            Voc foundVoc = entityManager.find(Voc.class, vocId);
            assertThat(foundVoc.getCompensation()).isNotNull();
            assertThat(foundVoc.getCompensation().getId()).isEqualTo(compensation.getId());
            assertThat(foundVoc.getCompensation().getAmount()).isEqualTo(compensation.getAmount());
        }

        @DisplayName("주어진 VOC id로 VOC를 찾을 수 없으면")
        @Nested
        class Context_with_not_exist_voc_id {

            @BeforeEach
            void setUp() {
                Voc voc = entityManager.find(Voc.class, vocId);
                if (voc != null) {
                    entityManager.remove(voc);
                }
            }

            @DisplayName("VocNotFoundException을 던진다")
            @Test
            void it_throws_voc_not_found_exception() {
                assertThrows(VocNotFoundException.class,
                        () -> compensationCreator.create(vocId, 100000));
            }
        }
    }
}
