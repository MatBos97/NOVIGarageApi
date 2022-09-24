package mathijs.bos.NOVIGarageApi.CustomAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/CustomAction")
public class CustomActionController {


    @Autowired
    private CustomActionRepository customActionRepository;

    @Secured({"ROLE_MECHANIC"})
    @GetMapping("/All")
    List<CustomAction> all(){
        return customActionRepository.findAll();
    }

    @Secured({"ROLE_MECHANIC"})
    @GetMapping("/{id}")
    CustomAction findCustomAction(@PathVariable Long id){
        return customActionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Custom action with id: " + id + " was not found."));
    }

    @Secured({"ROLE_MECHANIC"})
    @PostMapping()
    CustomAction newCustomAction(@RequestBody CustomAction newCustomAction){
        return customActionRepository.save(newCustomAction);
    }

    @Secured({"ROLE_MECHANIC"})
    @PutMapping("/{id}")
    CustomAction replaceCustomAction(@RequestBody CustomAction newCustomAction, @PathVariable Long id){
        return customActionRepository.findById(id)
                .map(customAction -> {
                    customAction.setRepair(newCustomAction.getRepair());
                    customAction.setDescription(newCustomAction.getDescription());
                    customAction.setPrice(newCustomAction.getPrice());
                    return customActionRepository.save(customAction);
                })
                .orElseGet(() -> {
                    newCustomAction.setId(id);
                    return customActionRepository.save(newCustomAction);
                });
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    void deleteCustomAction(@PathVariable Long id){
        customActionRepository.deleteById(id);
    }
}
