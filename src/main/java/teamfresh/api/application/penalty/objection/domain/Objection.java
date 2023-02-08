package teamfresh.api.application.penalty.objection.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import teamfresh.api.application.penalty.domain.Penalty;

/** 페널티 이의제기 엔티티 */
@Getter
@Entity
public class Objection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @Column(length = 500)
    private String content;

    protected Objection() {}

    public Objection(final Long id, final Penalty penalty, final String content) {
        this.id = id;
        this.penalty = penalty;
        this.content = content;
    }
}
