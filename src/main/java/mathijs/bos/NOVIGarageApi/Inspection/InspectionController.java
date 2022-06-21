package mathijs.bos.NOVIGarageApi.Inspection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Inspection")
public class InspectionController {


    @Autowired
    private InspectionRepository inspectionRepository;

    @GetMapping("/{id}")
    Inspection getInspection(@PathVariable Long id){
        return inspectionRepository.findById(id)
                .orElseThrow(() -> new InspectionNotFoundException(id));
    }

    @GetMapping("/all")
    List<Inspection> allInspections(){
        return inspectionRepository.findAll();
    }

    @PostMapping()
    Inspection newInspection(@RequestBody Inspection newInspection){
        return inspectionRepository.save(newInspection);
    }

    @PutMapping("/{id}")
    Inspection replaceInspection(@RequestBody Inspection newInspection, @PathVariable Long id){
        return inspectionRepository.findById(id)
                .map(inspection -> {
                    inspection.setDateOfInspection(newInspection.getDateOfInspection());
                    return inspectionRepository.save(inspection);
                })
                .orElseGet(() ->{
                    newInspection.setId(id);
                    return inspectionRepository.save(newInspection);
                });
    }

    @DeleteMapping("/{id}")
    void deleteInspection(@PathVariable Long id){
        inspectionRepository.deleteById(id);
    }


}
