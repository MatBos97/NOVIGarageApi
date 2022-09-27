package mathijs.bos.NOVIGarageApi.Car;

import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Car.CarAlreadyExistsException;
import mathijs.bos.NOVIGarageApi.Car.CarNotFoundException;
import mathijs.bos.NOVIGarageApi.Car.CarRepository;
import mathijs.bos.NOVIGarageApi.Exceptions.AlreadyExistsException;
import mathijs.bos.NOVIGarageApi.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Car")
public class CarController {


    @Autowired
    private CarRepository carRepository;

    @Secured({"ROLE_MECHANIC", "ROLE_RECEPTIONIST"})
    @GetMapping("/all")
    List<Car>  all(){
        return carRepository.findAll();
    }

    @Secured({"ROLE_MECHANIC", "ROLE_RECEPTIONIST"})
    @GetMapping("/{id}")
    Car one(@PathVariable Long id){
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car", "Id", id.toString()));
    }

    @Secured({"ROLE_MECHANIC", "ROLE_RECEPTIONIST"})
    @PostMapping()
    Car newCar(@RequestBody Car newCar){
        if (carRepository.existsByNumberPlateAllIgnoreCase(newCar.getNumberPlate())){
            throw new AlreadyExistsException(Car.class.getName(), "number plate", newCar.getNumberPlate());
        }
        return carRepository.save(newCar);
    }

    @Secured({"ROLE_MECHANIC", "ROLE_RECEPTIONIST"})
    @PutMapping("/{id}")
    Car replaceCar(@RequestBody Car newCar, @PathVariable long id){
        return carRepository.findById(id)
                .map(car -> {
                    car.setCustomer(newCar.getCustomer());
                    car.setNumberPlate(newCar.getNumberPlate());
                    return carRepository.save(car);
                })
                .orElseGet(() -> {
                    newCar.setId(id);
                    return carRepository.save(newCar);
                });
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable long id){
        carRepository.deleteById(id);
    }
}
