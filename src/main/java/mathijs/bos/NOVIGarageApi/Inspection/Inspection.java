package mathijs.bos.NOVIGarageApi.Inspection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Part.Part;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

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



}