package mathijs.bos.NOVIGarageApi.CarFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Car.Car;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car_file")
public class CarFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_file_seq")
    @SequenceGenerator(name = "car_file_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    public CarFile(Car car, String fileName, String fileType, byte[] data) {
        this.car = car;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}