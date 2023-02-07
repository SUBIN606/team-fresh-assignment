package teamfresh.api.application.penalty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.penalty.domain.PenaltyRepository;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;

@RequiredArgsConstructor
@Service
public class PenaltyReader {

    private final PenaltyRepository repository;

    /**
     * 주어진 id로 페널티 정보를 찾아 반환합니다.
     *
     * @param id 페널티 id
     * @throws PenaltyNotFoundException id로 페널티를 찾지 못한 경우 던짐
     */
    public Penalty read(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PenaltyNotFoundException(id));
    }
}
