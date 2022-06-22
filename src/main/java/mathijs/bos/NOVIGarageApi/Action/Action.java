package mathijs.bos.NOVIGarageApi.Action;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_seq")
    @SequenceGenerator(name = "action_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "action_description", nullable = false)
    private String actionDescription;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Action(String actionDescription, BigDecimal price) {
        this.actionDescription = actionDescription;
        this.price = price;
    }
}