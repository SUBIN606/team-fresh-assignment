package teamfresh.api.application.voc.blame.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/** 귀책 엔티티 */
@Entity
public class Blame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 귀책 당사자 */
    @Enumerated(EnumType.STRING)
    private BlameTarget target;

    /** 귀책 사유 */
    @Column(length = 500)
    private String cause;

    protected Blame() {}

    private Blame(final Long id, final BlameTarget target, final String cause) {
        this.id = id;
        this.target = target;
        this.cause = cause;
    }

    public static Blame of(Long id, BlameTarget target, String cause) {
        return new Blame(id, target, cause);
    }

    public static Blame of(BlameTarget target, String cause) {
        return new Blame(null, target, cause);
    }

    public Long getId() {
        return id;
    }

    public BlameTarget getTarget() {
        return target;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public String toString() {
        return "Blame{" +
                "id=" + id +
                ", target=" + target +
                ", cause='" + cause + '\'' +
                '}';
    }
}
