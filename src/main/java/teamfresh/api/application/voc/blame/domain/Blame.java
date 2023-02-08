package teamfresh.api.application.voc.blame.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

/** 귀책 엔티티 */
@Getter
@Entity
public class Blame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 귀책 당사자 */
    @Enumerated(EnumType.STRING)
    private BlameTarget target;

    /** 귀책 당사 회사 id */
    private Long company_id;

    /** 귀책 사유 */
    @Column(length = 500)
    private String cause;

    protected Blame() {}

    public Blame(final Long id, final BlameTarget target, final Long company_id, final String cause) {
        this.id = id;
        this.target = target;
        this.company_id = company_id;
        this.cause = cause;
    }

    public static Blame of(Long id, BlameTarget target, String cause) {
        return new Blame(id, target, null, cause);
    }

    public static Blame of(BlameTarget target, String cause) {
        return new Blame(null, target, null, cause);
    }

    public static Blame of(BlameTarget target, Long company_id, String cause) {
        return new Blame(null, target, company_id, cause);
    }

    public static Blame of(Long id, BlameTarget target, Long company_id, String cause) {
        return new Blame(id, target, company_id, cause);
    }
}
