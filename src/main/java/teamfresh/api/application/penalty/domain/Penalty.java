package teamfresh.api.application.penalty.domain;

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
import teamfresh.api.application.penalty.objection.domain.Objection;


/** 페널티 엔티티 */
@Getter
@Entity
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compensation_id")
    private Compensation compensation;

    /** 페널티 소유자 */
    private Long owner;

    /** 페널티 내용 */
    @Column(length = 500)
    private String content;

    /** 페널티 확인 여부 */
    @Column(name = "is_read", columnDefinition = "tinyint")
    private Boolean read;

    /** 페널티 인정 여부 */
    @Column(columnDefinition = "tinyint")
    private Boolean confirmed;

    @OneToOne(mappedBy = "penalty", fetch = FetchType.LAZY)
    private Objection objection;

    protected Penalty() {
    }

    private Penalty(final Long id, final Compensation compensation, final Long owner, final String content, final Boolean read, final Boolean confirmed) {
        this.id = id;
        this.compensation = compensation;
        this.owner = owner;
        this.content = content;
        this.read = read;
        this.confirmed = confirmed;
    }

    public static Penalty of(Compensation compensation, Long owner) {
        return new Penalty(null, compensation, owner, null, false, false);
    }

    public static Penalty of(Compensation compensation, Long owner, String content) {
        return new Penalty(null, compensation, owner, content, false, false);
    }

    public static Penalty of(Long id, Compensation compensation, Long owner) {
        return new Penalty(id, compensation, owner, null, false, false);
    }

    public static Penalty of(Long id, Compensation compensation, Long owner, String content) {
        return new Penalty(id, compensation, owner, content, false, false);
    }

    /** 페널티를 확인하면 read 상태를 true로 변경합니다. */
    public void read() {
        this.read = Boolean.TRUE;
    }

    /** 페널티에 대한 이의제기 */
    public void object(Objection objection) {
        this.objection = objection;
    }
}
