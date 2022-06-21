package mathijs.bos.NOVIGarageApi.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Action")
public class ActionController {


    @Autowired
    private ActionRepository actionRepository;

    @GetMapping("/All")
    List<Action> all(){
        return actionRepository.findAll();
    }

    @GetMapping("/{id}")
    Action findAction(@PathVariable Long id){
        return actionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action with id: " + id + " was not found."));
    }

    @PostMapping()
    Action newAction(@RequestBody Action newAction){
        return actionRepository.save(newAction);
    }

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

    @DeleteMapping("/{id}")
    void deleteAction(@PathVariable Long id){
        actionRepository.deleteById(id);
    }
}
