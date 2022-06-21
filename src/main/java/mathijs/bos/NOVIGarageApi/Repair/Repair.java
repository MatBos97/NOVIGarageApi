package mathijs.bos.NOVIGarageApi.Repair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Action.Action;
import mathijs.bos.NOVIGarageApi.Part.Part;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "repair")
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repair_seq")
    @SequenceGenerator(name = "repair_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_of_repair", nullable = false)
    private Date dateOfRepair;

    @ManyToMany
    @JoinTable(name = "repair_parts",
            joinColumns = @JoinColumn(name = "repair_id"),
            inverseJoinColumns = @JoinColumn(name = "parts_id"))
    private Collection<Part> parts = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "repair_actions",
            joinColumns = @JoinColumn(name = "repair_id"),
            inverseJoinColumns = @JoinColumn(name = "actions_id"))
    private Collection<Action> actions = new ArrayList<>();

    public Repair(Date dateOfRepair, Collection<Part> parts, Collection<Action> actions) {
        this.dateOfRepair = dateOfRepair;
        this.parts = parts;
        this.actions = actions;
    }
}