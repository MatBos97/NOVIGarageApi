package mathijs.bos.NOVIGarageApi.CarFile;

import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Car.CarNotFoundException;
import mathijs.bos.NOVIGarageApi.Car.CarRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/CarFile")
public class CarFileController {


    @Autowired
    private CarFileRepository carFileRepository;
    @Autowired
    private CarRepository carRepository;

    @GetMapping("/{carId}")
    ResponseEntity<byte[]> getFile(@PathVariable Long carId){
        CarFile carFile = carFileRepository.findByCar_Id(carId)
                .orElseThrow();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + carFile.getFileName() + "\"")
                .body(carFile.getData());
    }

    @PostMapping("/{carId}")
    CarFile saveFile(@RequestParam("file") @NotNull MultipartFile file, @PathVariable Long carId){
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
        try {
            String filename = file.getOriginalFilename();
            String fileType = file.getContentType();
            byte[] fileData = file.getBytes();
            return carFileRepository.save(new CarFile(car, filename, fileType, fileData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}