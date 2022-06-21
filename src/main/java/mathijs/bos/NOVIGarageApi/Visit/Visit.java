package mathijs.bos.NOVIGarageApi.Visit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Inspection.Inspection;
import mathijs.bos.NOVIGarageApi.Receipt.Receipt;
import mathijs.bos.NOVIGarageApi.Repair.Repair;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_seq")
    @SequenceGenerator(name = "visit_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "repair_id")
    private Repair repair;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @Column(name = "status_of_visit", nullable = false, length = 50)
    private StatusOfVisit statusOfVisit;


    public Visit(Car car, Inspection inspection, Repair repair, StatusOfVisit statusOfVisit) {
        this.car = car;
        this.inspection = inspection;
        this.repair = repair;
        this.statusOfVisit = statusOfVisit;
    }

    public enum StatusOfVisit{
        QUE_INSPECTION,
        UNDERGOING_INSPECTION,
        QUE_MAINTENANCE,
        UNDERGOING_MAINTENANCE,
        READY_FOR_PICKUP,
        DONE
    }

}