package teamfresh.api.application.penalty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.penalty.domain.Penalty;

/** 페널티 읽음 처리 담당 */
@RequiredArgsConstructor
@Service
public class PenaltyReadUpdater {

    private final PenaltyReader penaltyReader;

    /**
     * 페널티 내용을 APP에서 확인하면 페널티의 읽기 여부를 true로 변경합니다.
     */
    @Transactional
    public void update(Long id) {
        Penalty penalty = penaltyReader.read(id);
        penalty.read();
    }
}
