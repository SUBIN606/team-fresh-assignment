package teamfresh.api.application.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

/** 회사 엔티티 */
@Getter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120)
    private String name;

    @Column(length = 20)
    private String phone;

    private String address;

    protected Company() {
    }

    public Company(final Long id, final String name, final String phone, final String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
