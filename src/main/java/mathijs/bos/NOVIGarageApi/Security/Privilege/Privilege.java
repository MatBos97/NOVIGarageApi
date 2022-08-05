package mathijs.bos.NOVIGarageApi.Security.Privilege;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilege_seq")
    @SequenceGenerator(name = "privilege_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
    public Privilege(String name) {
        this.name = name;
    }
}