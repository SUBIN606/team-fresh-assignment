package teamfresh.api.application.voc.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamfresh.api.application.voc.blame.domain.Blame;
import teamfresh.api.application.voc.blame.domain.BlameTarget;
import teamfresh.api.application.voc.domain.Voc;
import teamfresh.api.application.voc.domain.VocRepository;

/** VOC 등록 담당 서비스 */
@RequiredArgsConstructor
@Service
public class VocCreator {

    private final VocRepository repository;

    /**
     * 주어진 데이터를 이용해 VOC를 등록합니다.
     *
     * @param command VOC 생성 요청 커맨드
     * @return 생성된 VOC
     */
    @Transactional
    public Voc create(Command command) {
        Voc voc = Voc.of(
                command.content,
                command.customerManagerId,
                command.createdBy
        );

        voc.setBlame(
                Blame.of(
                        command.target,
                        command.targetCompanyId,
                        command.cause
                )
        );

        return repository.save(voc);
    }

    /**
     * VOC 생성 요청 커맨드 객체입니다. VOC 생성에 필요한 모든 값을 포함합니다.
     */
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Command {
        private String content;
        private BlameTarget target;
        private Long targetCompanyId;
        private String cause;
        private Long customerManagerId;
        private Long createdBy;
    }
}
