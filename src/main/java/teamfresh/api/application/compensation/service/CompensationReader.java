package teamfresh.api.application.compensation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.domain.CompensationRepository;
import teamfresh.api.application.compensation.exception.CompensationNotFoundException;

/** 배상 정보 조회 담당 */
@RequiredArgsConstructor
@Service
public class CompensationReader {

    private final CompensationRepository repository;

    /**
     * 주어진 id에 해당하는 배상 정보를 조회 후 반환합니다.
     *
     * @param id 배상 정보 id
     * @return 배상 정보
     * @throws CompensationNotFoundException 주어진 id로 배상 정보를 찾을 수 없는 경우 던짐
     */
    @Transactional(readOnly = true)
    public Compensation read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CompensationNotFoundException(id));
    }
}
