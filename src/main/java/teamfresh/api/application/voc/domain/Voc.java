package teamfresh.api.application.voc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import teamfresh.api.application.compensation.domain.Compensation;
import teamfresh.api.application.voc.blame.domain.Blame;

/** VOC(Voice of Customer) 엔티티 */
@Getter
@Entity
public class Voc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 클레임 내용 */
    @Column(length = 500)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blame_id")
    private Blame blame;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compensation_id")
    private Compensation compensation;

    /** 클레임을 제기한 고객사의 담당자 */
    private Long customerManagerId;

    private Long createdBy;

    protected Voc() {}

    private Voc(final Long id, final String content, final Blame blame, final Compensation compensation, final Long customerManagerId, final Long createdBy) {
        this.id = id;
        this.content = content;
        this.blame = blame;
        this.compensation = compensation;
        this.customerManagerId = customerManagerId;
        this.createdBy = createdBy;
    }

    public static Voc of(Long id, String content, Long createdBy) {
        return new Voc(id, content, null, null, null, createdBy);
    }

    public static Voc of(String content, Long createdBy) {
        return new Voc(null, content, null, null, null, createdBy);
    }

    public static Voc withBlame(String content, Blame blame, Long customerManagerId, Long createdBy) {
        return new Voc(0L, content, blame, null, customerManagerId, createdBy);
    }

    /**
     * 해당 VOC 건에 대한 배상을 접수합니다.
     */
    public void registerCompensation(final Compensation compensation) {
        this.compensation = compensation;
    }
}
