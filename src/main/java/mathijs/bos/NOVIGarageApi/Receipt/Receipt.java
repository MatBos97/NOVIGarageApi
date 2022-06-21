package mathijs.bos.NOVIGarageApi.Receipt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mathijs.bos.NOVIGarageApi.Visit.Visit;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_seq")
    @SequenceGenerator(name = "receipt_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    public Receipt(Visit visit, String fileName, String fileType, byte[] data) {
        this.visit = visit;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}