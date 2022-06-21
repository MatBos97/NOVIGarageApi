package mathijs.bos.NOVIGarageApi.CarFile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarFileRepository extends JpaRepository<CarFile, Long> {
    Optional<CarFile> findByCar_Id(Long id);
}