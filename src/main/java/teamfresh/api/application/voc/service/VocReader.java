package teamfresh.api.application.voc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;
import teamfresh.api.application.voc.exception.VocNotFoundException;

/** VOC 조회 담당 */
@Service
public class VocReader {

    private final VocRepository repository;

    public VocReader(final VocRepository repository) {
        this.repository = repository;
    }

    /**
     * 주어진 식별자로 찾은 VOC를 반환합니다.
     *
     * @param id VOC 식별자
     * @return 식별자로 조회한 VOC
     * @throws VocNotFoundException 식별자로 VOC를 찾지 못한 경우 던짐
     */
    @Transactional(readOnly = true)
    public Voc read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VocNotFoundException(id));
    }
}
