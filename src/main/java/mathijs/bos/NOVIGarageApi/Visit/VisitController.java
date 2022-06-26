package mathijs.bos.NOVIGarageApi.Visit;

import mathijs.bos.NOVIGarageApi.Car.Car;
import mathijs.bos.NOVIGarageApi.Car.CarNotFoundException;
import mathijs.bos.NOVIGarageApi.Car.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Visit")
public class VisitController {


    @Autowired
    private VisitRepository visitRepository;

    @GetMapping()
    List<Visit> all(){
        return visitRepository.findAll();
    }

    @GetMapping("/{id}")
    Visit getVisit(@PathVariable Long id){
        return visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit with id: " + id + " was not found."));
    }

    @GetMapping("/ReadyForPickUp")
    List<Visit> getReadyForPickUp(){
        return visitRepository.findByStatusOfVisit(Visit.StatusOfVisit.READY_FOR_PICKUP);
    }

    @PostMapping()
    Visit newVisit(@RequestBody Visit newVisit){
        List<Visit> visits = visitRepository.findByCar_NumberPlateIgnoreCase(newVisit.getCar().getNumberPlate());
        for (Visit visit : visits) {
            if (visit.getStatusOfVisit() != Visit.StatusOfVisit.DONE) {
                throw new RuntimeException("This car is already in a visit state. A car can only have one visit open at a time.");
            }
        }
        return visitRepository.save(newVisit);
    }

    @PutMapping("/{id}")
    Visit replaceVisit(@RequestBody Visit newVisit, @PathVariable Long id){
        return visitRepository.findById(id)
                .map(visit -> {
                    visit.setCar(newVisit.getCar());
                    visit.setInspection(newVisit.getInspection());
                    visit.setRepair(newVisit.getRepair());
                    visit.setReceipt(newVisit.getReceipt());
                    visit.setStatusOfVisit(newVisit.getStatusOfVisit());
                    return visitRepository.save(visit);
                }).orElseGet(() -> {
                    newVisit.setId(id);
                    return visitRepository.save(newVisit);
                });
    }

    @DeleteMapping("/{id}")
    void deleteVisit(@PathVariable Long id){
        visitRepository.deleteById(id);
    }
}
