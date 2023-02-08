package teamfresh.api.web.penalties.read;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import teamfresh.api.application.penalty.service.PenaltyReadUpdater;

/** 페널티 읽음 처리 요청 담당 */
@RequiredArgsConstructor
@RequestMapping("/penalties/{id}/read")
@RestController
public class PenaltyReadUpdateController {

    private final PenaltyReadUpdater readUpdater;

    /** 주어진 페널티 id에 해당하는 페널티 정보를 읽음 처리 합니다. */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping
    public void handlePenaltyRead(@PathVariable Long id) {
        readUpdater.update(id);
    }
}
