package teamfresh.api.application.penalty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.service.CompensationReader;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.domain.PenaltyRepository;

/** 페널티 발급 담당 */
@RequiredArgsConstructor
@Service
public class PenaltyIssuer {

    private final CompensationReader compensationReader;
    private final PenaltyRepository repository;

    /**
     * 주어진 배상정보 id와 페널티 대상 id를 이용해 페널티를 생성 후 반환합니다.
     *
     * @param compensationId 배상 정보 id
     * @param owner 페널티 대상
     * @return 등록된 페널티 정보
     */
    @Transactional
    public Penalty issue(Long compensationId, Long owner) {
        Compensation compensation = compensationReader.read(compensationId);
        return repository.save(
                Penalty.of(compensation, owner)
        );
    }
}
