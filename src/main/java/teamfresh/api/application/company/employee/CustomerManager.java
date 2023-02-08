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

/** 고객사 담당자 엔티티 */
@Getter
@Entity
public class CustomerManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(
            name = "customer_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Company company;

    @Column(length = 30)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String department;

    @Column(length = 20)
    private String jobTitle;

    protected CustomerManager() {
    }

    public CustomerManager(final Long id, final Company company, final String name, final String phone, final String email, final String department, final String jobTitle) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.jobTitle = jobTitle;
    }
}
