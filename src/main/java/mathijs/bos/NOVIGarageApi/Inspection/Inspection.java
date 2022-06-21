package mathijs.bos.NOVIGarageApi.Inspection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Issue.Issue;
import mathijs.bos.NOVIGarageApi.Part.Part;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inspection")
public class Inspection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_seq")
    @SequenceGenerator(name = "inspection_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_of_inspection", nullable = false)
    private Date dateOfInspection;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "inspection_id")
    private Set<Issue> issues = new LinkedHashSet<>();

    public Inspection(Date dateOfInspection, Set<Issue> issues) {
        this.dateOfInspection = dateOfInspection;
        this.issues = issues;
    }
}