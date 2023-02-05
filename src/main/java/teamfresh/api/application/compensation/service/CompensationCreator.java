package teamfresh.api.application.compensation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.domain.CompensationRepository;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.service.VocReader;

/** 배상 정보 생성 담당 */
@RequiredArgsConstructor
@Service
public class CompensationCreator {

    private final VocReader vocReader;
    private final CompensationRepository repository;

    /**
     * 배상정보를 등록합니다. 그리고 해당하는 VOC 건에 배상을 접수합니다.
     *
     * @param vocId 배상을 등록할 VOC의 식별자
     * @param amount 배상 금액
     * @return 배상 정보
     */
    @Transactional
    public Compensation create(Long vocId, int amount) {
        Voc voc = vocReader.read(vocId);

        Compensation compensation = repository.save(
                Compensation.of(amount)
        );
        voc.registerCompensation(compensation);
        return compensation;
    }
}
