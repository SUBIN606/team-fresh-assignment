package teamfresh.api.application.company.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import teamfresh.api.application.company.domain.Company;

/** 운송사 기사 엔티티*/
@Getter
@Entity
public class DeliveryMan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(
            name = "carrier_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Company company;

    @Column(length = 30)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    private String region;

    protected DeliveryMan() {
    }

    public DeliveryMan(final Long id, final Company company, final String name, final String phone, final String region) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.phone = phone;
        this.region = region;
    }
}
