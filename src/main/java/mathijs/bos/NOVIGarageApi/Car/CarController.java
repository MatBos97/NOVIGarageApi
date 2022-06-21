package mathijs.bos.NOVIGarageApi.Car;

import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Car.CarAlreadyExistsException;
import mathijs.bos.NOVIGarageApi.Car.CarNotFoundException;
import mathijs.bos.NOVIGarageApi.Car.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Car")
public class CarController {


    @Autowired
    private CarRepository carRepository;

    @GetMapping("/all")
    List<Car>  all(){
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    Car one(@PathVariable long id){
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @PostMapping()
    Car newCar(@RequestBody Car newCar){
        if (carRepository.existsByNumberPlateAllIgnoreCase(newCar.getNumberPlate())){
            throw new CarAlreadyExistsException(newCar.getNumberPlate());
        }
        return carRepository.save(newCar);
    }

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

    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable long id){
        carRepository.deleteById(id);
    }
}
