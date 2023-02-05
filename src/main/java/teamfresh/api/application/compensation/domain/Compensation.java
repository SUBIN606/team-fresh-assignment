package teamfresh.api.application.compensation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

/** 배상정보 엔티티 */
@Getter
@Entity
public class Compensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    protected Compensation() {
    }

    private Compensation(final Long id, final int amount) {
        this.id = id;
        this.amount = amount;
    }

    public static Compensation of(Long id, int amount) {
        return new Compensation(id, amount);
    }

    public static Compensation of(int amount) {
        return new Compensation(null, amount);
    }
}
