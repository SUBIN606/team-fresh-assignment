package teamfresh.api.application.compensation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import teamfresh.api.application.penalty.domain.Penalty;

/** 배상정보 엔티티 */
@Getter
@Entity
public class Compensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @OneToOne(mappedBy = "compensation", fetch = FetchType.LAZY)
    private Penalty penalty;

    protected Compensation() {
    }

    public Compensation(final Long id, final int amount, final Penalty penalty) {
        this.id = id;
        this.amount = amount;
        this.penalty = penalty;
    }

    public static Compensation of(Long id, int amount) {
        return new Compensation(id, amount, null);
    }

    public static Compensation of(int amount) {
        return new Compensation(null, amount, null);
    }
}
