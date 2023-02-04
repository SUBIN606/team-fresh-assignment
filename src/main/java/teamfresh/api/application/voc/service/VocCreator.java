package teamfresh.api.application.voc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.voc.blame.service.BlameCreator;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;

/** VOC 등록 담당 서비스 */
@Service
public class VocCreator {

    private final BlameCreator blameCreator;
    private final VocRepository repository;

    public VocCreator(final BlameCreator blameCreator, final VocRepository repository) {
        this.blameCreator = blameCreator;
        this.repository = repository;
    }

    /**
     * 주어진 데이터를 이용해 VOC를 등록합니다.
     *
     * @param createdBy VOC 등록 담당자 id
     * @param content 클레임 내용
     * @param target 귀책 대상
     * @param cause 귀책 사유
     */
    @Transactional
    public Voc create(Long createdBy,
                       String content,
                       BlameTarget target,
                       String cause) {
        return repository.save(
                Voc.withBlame(
                        content,
                        createdBy,
                        blameCreator.create(target, cause)
                )
        );
    }
}
