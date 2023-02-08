package teamfresh.api.application.compensation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.domain.CompensationRepository;

import java.util.List;

/** 배상 목록 조회 담당 */
@RequiredArgsConstructor
@Service
public class CompensationListReader {

    private final CompensationRepository repository;

    /** 배상 목록을 조회 후 반환합니다. */
    @Transactional(readOnly = true)
    public List<Compensation> read() {
        return repository.findAllWithFetch();
    }
}
