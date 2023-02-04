package teamfresh.api.application.voc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import teamfresh.api.application.voc.blame.domain.Blame;

/** VOC(Voice of Customer) 엔티티 */
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

    private Long createdBy;

    protected Voc() {}

    private Voc(final Long id, final String content, final Long createdBy) {
        this.id = id;
        this.content = content;
        this.createdBy = createdBy;
    }

    public static Voc of(Long id, String content, Long createdBy) {
        return new Voc(id, content, createdBy);
    }

    public static Voc of(String content, Long createdBy) {
        return new Voc(0L, content, createdBy);
    }

    public static Voc withBlame(String content, Long createdBy, Blame blame) {
        Voc voc = new Voc(0L, content, createdBy);
        voc.setBlame(blame);
        return voc;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Blame getBlame() {
        return blame;
    }

    private void setBlame(final Blame blame) {
        this.blame = blame;
    }

    @Override
    public String toString() {
        return "Voc{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", blame=" + blame +
                ", createdBy=" + createdBy +
                '}';
    }
}
