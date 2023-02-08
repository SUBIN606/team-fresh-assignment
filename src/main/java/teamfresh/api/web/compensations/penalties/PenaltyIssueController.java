package teamfresh.api.web.compensations.penalties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import teamfresh.api.application.penalty.service.PenaltyIssuer;

@RequiredArgsConstructor
@RequestMapping("/compensations/{compensationId}/penalties")
@RestController
public class PenaltyIssueController {

    private final PenaltyIssuer penaltyIssuer;

    /**
     * 배상 id와 페널티를 발급하기 위한 정보를 받아 페널티를 발급합니다.
     *
     * @param compensationId 페널티 배상 정보
     * @param request 페널티 발급 요청 객체
     * @return 발급한 페널티의 id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response handleIssuePenalty(
            @PathVariable Long compensationId,
            @RequestBody Request request
    ) {
        return new Response(
                penaltyIssuer.issue(
                        compensationId,
                        request.getOwner(),
                        request.getContent()
                ).getId()
        );
    }

    /** 페널티 발급 요청 객체 */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long owner;
        private String content;
    }

    /** 페널티 발급 응답 객체 */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
    }
}
