package teamfresh.api.web.vocs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.penalty.domain.Penalty;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.service.VocListReader;

import java.util.List;
import java.util.stream.Collectors;

/** VOC 목록 조회 요청 담당 */
@RequiredArgsConstructor
@RequestMapping("/vocs")
@RestController
public class VocListController {

    private final VocListReader vocListReader;

    /** VOC 목록을 모두 조회 후 응답합니다. */
    @GetMapping
    public Response handleReadVocList() {
        return new Response(
                vocListReader.read()
                        .stream()
                        .map(Response.VocDto::new)
                        .collect(Collectors.toList())
        );
    }

    /** VOC 목록 응답 객체 */
    @Getter
    @AllArgsConstructor
    public static class Response {

        private List<VocDto> vocs;

        @Getter
        public static class VocDto {
            private Long id;
            private String content;
            private BlameDto blame;
            private CompensationDto compensation;
            private PenaltyDto penalty;

            public VocDto(Voc voc) {
                this.id = voc.getId();
                this.content = voc.getContent();
                this.blame = new BlameDto(voc.getBlame());
                this.compensation = new CompensationDto(voc.getCompensation());
                this.penalty = new PenaltyDto(voc.getCompensation().getPenalty());
            }

            @Getter
            public static class BlameDto {
                private Long id;
                private BlameTarget target;
                private Long targetCompanyId;
                private String cause;

                public BlameDto(Blame blame) {
                    if (blame != null) {
                        this.id = blame.getId();
                        this.target = blame.getTarget();
                        this.targetCompanyId = blame.getCompany_id();
                        this.cause = blame.getCause();
                    }
                }
            }

            @Getter
            public static class CompensationDto {
                private Long id;
                private int amount;

                public CompensationDto(Compensation compensation) {
                    if (compensation != null) {
                        this.id = compensation.getId();
                        this.amount = compensation.getAmount();
                    }
                }
            }

            @Getter
            public static class PenaltyDto {
                private Long id;
                private Boolean read;
                private Boolean confirmed;

                public PenaltyDto(Penalty penalty) {
                    if (penalty != null) {
                        this.id = penalty.getId();
                        this.read = penalty.getRead();
                        this.confirmed = penalty.getConfirmed();
                    }
                }
            }
        }
    }
}
