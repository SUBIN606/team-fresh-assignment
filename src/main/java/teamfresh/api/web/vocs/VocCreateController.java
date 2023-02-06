package teamfresh.api.web.vocs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.service.VocCreator;

/** VOC 생성 요청 담당 */
@RequiredArgsConstructor
@RequestMapping("/vocs")
@RestController
public class VocCreateController {

    private final VocCreator vocCreator;

    /**
     * VOC 생성 요청데이터를 받아 VOC를 생성 합니다.
     * 생성된 VOC의 id를 응답합니다.
     *
     * @param request VOC 생성 요청 객체
     * @return 생성된 VOC의 id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response handleVocCreate(@RequestBody Request request) {
        VocCreator.Command command = new VocCreator.Command(
                request.getContent(),
                request.getTarget(),
                request.getCause(),
                request.getCustomerManagerId(),
                request.getCreatedBy()
        );
        return new Response(vocCreator.create(command).getId());
    }

    /** VOC 생성 요청 객체 */
    @Getter
    @AllArgsConstructor
    public static class Request {
        private String content;
        private BlameTarget target;
        private String cause;
        private Long customerManagerId;
        private Long createdBy;
    }

    /** VOC 생성 응답 객체 */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
    }
}
