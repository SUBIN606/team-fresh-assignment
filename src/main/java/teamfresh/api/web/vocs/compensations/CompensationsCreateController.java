package teamfresh.api.web.vocs.compensations;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import teamfresh.api.application.compensation.service.CompensationCreator;

/** 배상 정보 등록 요청 담당 */
@RequiredArgsConstructor
@RequestMapping("/vocs/{vocId}/compensations")
@RestController
public class CompensationsCreateController {

    private final CompensationCreator compensationCreator;

    /**
     * VOC 건에 해당하는 배상 정보를 등록한 후 배상 정보의 id를 응답합니다.
     *
     * @param vocId 배상 정보를 등록할 VOC의 id
     * @param request 배상 정보 등록 요청 객체
     * @return 생성된 배상정보의 id
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response handleCompensationCreate(
            @PathVariable Long vocId,
            @Validated @RequestBody Request request
    ) {
        return new Response(
                compensationCreator.create(vocId, request.getAmount()).getId()
        );
    }

    /** 배상 정보 생성 요청 객체 */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @Min(0)
        private int amount;
    }

    /** 배상 정보 생성 응답 객체 */
    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
    }
}
