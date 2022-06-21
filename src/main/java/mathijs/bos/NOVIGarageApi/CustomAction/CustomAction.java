package mathijs.bos.NOVIGarageApi.CustomAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Repair.Repair;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "custom_action")
public class CustomAction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_action_seq")
    @SequenceGenerator(name = "custom_action_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "repair_id", nullable = false)
    private Repair repair;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Float price;

}