package teamfresh.api.web.vocs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public Response handleVocCreate(@Validated @RequestBody Request request) {
        VocCreator.Command command = new VocCreator.Command(
                request.getContent(),
                request.getTarget(),
                request.getTargetCompanyId(),
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
        @NotBlank(message = "VOC 내용을 입력하세요.")
        @Size(max = 500)
        private String content;

        private BlameTarget target;

        @Positive
        private Long targetCompanyId;

        @NotBlank(message = "귀책 사유를 입력하세요.")
        @Size(max = 500)
        private String cause;

        @Positive
        private Long customerManagerId;

        @Positive
        private Long createdBy;
    }

    /** VOC 생성 응답 객체 */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
    }
}
