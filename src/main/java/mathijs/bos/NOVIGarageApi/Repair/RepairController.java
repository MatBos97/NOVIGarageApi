package mathijs.bos.NOVIGarageApi.Repair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Repair")
public class RepairController {


    @Autowired
    private RepairRepository repairRepository;

    @Secured("ROLE_MECHANIC")
    @GetMapping()
    List<Repair> all(){
        return repairRepository.findAll();
    }

    @Secured("ROLE_MECHANIC")
    @GetMapping("/{id}")
    Repair getRepair(@PathVariable Long id){
        return repairRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No repair with id: " + id + " was found."));
    }

    @Secured("ROLE_MECHANIC")
    @PostMapping()
    Repair newRepair(@RequestBody Repair newRepair){
        return repairRepository.save(newRepair);
    }

    @Secured("ROLE_MECHANIC")
    @PutMapping("/{id}")
    Repair replaceRepair(@RequestBody Repair newRepair, @PathVariable Long id){
        return repairRepository.findById(id)
                .map(repair -> {
                    repair.setDateOfRepair(newRepair.getDateOfRepair());
                    repair.setActions(newRepair.getActions());
                    repair.setParts(newRepair.getParts());
                    return repairRepository.save(newRepair);
                })
                .orElseGet(() -> {
                    newRepair.setId(id);
                    return repairRepository.save(newRepair);
                });
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void deleteRepair(@PathVariable Long id){
        repairRepository.deleteById(id);
    }
}
