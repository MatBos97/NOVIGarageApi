package mathijs.bos.NOVIGarageApi.Part;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_seq")
    @SequenceGenerator(name = "part_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Part(String partName, BigDecimal price) {
        this.partName = partName;
        this.price = price;
    }
}