package mathijs.bos.NOVIGarageApi.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Part")
public class PartController {


    @Autowired
    private PartRepository partRepository;

    @Secured({"ROLE_MECHANIC"})
    @GetMapping("/All")
    List<Part> all(){
        return partRepository.findAll();
    }

    @Secured({"ROLE_MECHANIC"})
    @GetMapping("/{id}")
    Part findPart(@PathVariable Long id){
        return partRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No part with id: " + id + " was found."));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    Part newPart(@RequestBody Part newPart){
        return partRepository.save(newPart);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    Part replacePart(@RequestBody Part newPart, @PathVariable Long id){
        return partRepository.findById(id)
                .map(part -> {
                    part.setPartName(newPart.getPartName());
                    part.setPrice(newPart.getPrice());
                    part.setStock(newPart.getStock());
                    return partRepository.save(part);
                })
                .orElseGet(() -> {
                    newPart.setId(id);
                    return partRepository.save(newPart);
                });
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    void deletePart(@PathVariable Long id){
        partRepository.deleteById(id);
    }
}
