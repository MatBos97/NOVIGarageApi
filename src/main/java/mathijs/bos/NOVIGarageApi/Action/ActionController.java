package mathijs.bos.NOVIGarageApi.Action;

import mathijs.bos.NOVIGarageApi.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Action")
public class ActionController {


    @Autowired
    private ActionRepository actionRepository;

    @Secured({"ROLE_MECHANIC"})
    @GetMapping("/All")
    List<Action> all(){
        return actionRepository.findAll();
    }

    @Secured({"ROLE_MECHANIC"})
    @GetMapping("/{id}")
    Action findAction(@PathVariable Long id){
        return actionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Action", "id", id.toString()));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    Action newAction(@RequestBody Action newAction){
        return actionRepository.save(newAction);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    Action replaceAction(@RequestBody Action newAction, @PathVariable Long id){
        return actionRepository.findById(id)
                .map(action -> {
                    action.setActionDescription(newAction.getActionDescription());
                    action.setPrice(newAction.getPrice());
                    return actionRepository.save(action);
                })
                .orElseGet(() -> {
                    newAction.setId(id);
                    return actionRepository.save(newAction);
                });
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    void deleteAction(@PathVariable Long id){
        actionRepository.deleteById(id);
    }
}
