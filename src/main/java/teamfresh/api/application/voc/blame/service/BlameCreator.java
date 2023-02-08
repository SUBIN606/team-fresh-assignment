package teamfresh.api.application.voc.blame.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameRepository;
import teamfresh.api.application.voc.blame.domain.BlameTarget;

/** 귀책 생성 담당 */
@Service
public class BlameCreator {

    private final BlameRepository repository;

    public BlameCreator(final BlameRepository repository) {
        this.repository = repository;
    }

    /**
     * 주어진 귀책 정보를 이용해 Blame 엔티티를 생성 및 저장 후 반환합니다.
     *
     * @param target 귀책 대상
     * @param companyId 귀책 당사 회사 id
     * @param cause 귀책 사유
     * @return 생성된 귀책 정보
     */
    @Transactional
    public Blame create(BlameTarget target,
                        Long companyId,
                        String cause) {
        return repository.save(Blame.of(target,companyId, cause));
    }
}
