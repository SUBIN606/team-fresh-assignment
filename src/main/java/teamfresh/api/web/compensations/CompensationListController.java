package teamfresh.api.web.compensations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.compensation.service.CompensationListReader;
import teamfresh.api.application.voc.domain.Voc;

import java.util.List;
import java.util.stream.Collectors;

/** 배상 목록 조회 요청 담당 */
@RequiredArgsConstructor
@RequestMapping("/compensations")
@RestController
public class CompensationListController {

    private final CompensationListReader compensationListReader;

    /** 배상 목록을 응답합니다. */
    @GetMapping
    public Response handleReadCompensationList() {
        return new Response(
                compensationListReader.read()
                        .stream()
                        .map(Response.CompensationDto::new)
                        .collect(Collectors.toList())
        );
    }

    /** 배상 목록 응답 객체*/
    @Getter
    @AllArgsConstructor
    public static class Response {
        private List<CompensationDto> compensations;

        @Getter
        public static class CompensationDto {
            private Long id;
            private int amount;
            private VocDto voc;

            public CompensationDto(Compensation compensation) {
                this.id = compensation.getId();
                this.amount = compensation.getAmount();
                this.voc = new VocDto(compensation.getVoc());
            }

            @Getter
            public static class VocDto {
                private Long id;
                private String content;

                public VocDto(Voc voc) {
                    if (voc != null) {
                        this.id = voc.getId();
                        this.content = voc.getContent();
                    }
                }
            }
        }
    }
}
