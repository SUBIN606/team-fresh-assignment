package teamfresh.api.application.voc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;

import java.util.List;

/** VOC 목록 조회 담당 */
@RequiredArgsConstructor
@Service
public class VocListReader {

    private final VocRepository repository;

    /** VOC 목록을 모두 조회합니다. */
    @Transactional(readOnly = true)
    public List<Voc> read() {
        return repository.findAllWithFetch();
    }
}
