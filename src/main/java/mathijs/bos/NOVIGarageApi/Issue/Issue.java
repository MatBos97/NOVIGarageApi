package mathijs.bos.NOVIGarageApi.Issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Inspection.Inspection;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_seq")
    @SequenceGenerator(name = "issue_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inspection_id", nullable = false)
    private Inspection inspection;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status_of_issue")
    private StatusOfIssue statusOfIssue;

    public Issue(Inspection inspection, String description, StatusOfIssue statusOfIssue) {
        this.inspection = inspection;
        this.description = description;
        this.statusOfIssue = statusOfIssue;
    }

    public enum StatusOfIssue{
        IN_QUE_FOR_REPAIR,
        DO_NOT_REPAIR,
        REPAIRED
    }

}