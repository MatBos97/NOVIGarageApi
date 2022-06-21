package mathijs.bos.NOVIGarageApi.Car;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByNumberPlateAllIgnoreCase(String numberPlate);

    Optional<Car> findByNumberPlateIgnoreCase(String numberPlate);


}