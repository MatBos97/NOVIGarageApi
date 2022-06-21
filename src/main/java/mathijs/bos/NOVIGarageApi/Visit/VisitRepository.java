package mathijs.bos.NOVIGarageApi.Visit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByCar_NumberPlateIgnoreCase(String numberPlate);

}