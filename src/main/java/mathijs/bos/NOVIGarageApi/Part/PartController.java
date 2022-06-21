package mathijs.bos.NOVIGarageApi.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Part")
public class PartController {


    @Autowired
    private PartRepository partRepository;

    @GetMapping("/All")
    List<Part> all(){
        return partRepository.findAll();
    }

    @GetMapping("/{id}")
    Part findPart(@PathVariable Long id){
        return partRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No part with id: " + id + " was found."));
    }

    @PostMapping()
    Part newPart(@RequestBody Part newPart){
        return partRepository.save(newPart);
    }

    @PutMapping("/{id}")
    Part replacePart(@RequestBody Part newPart, @PathVariable Long id){
        return partRepository.findById(id)
                .map(part -> {
                    part.setPartName(newPart.getPartName());
                    part.setPrice(newPart.getPrice());
                    return partRepository.save(part);
                })
                .orElseGet(() -> {
                    newPart.setId(id);
                    return partRepository.save(newPart);
                });
    }

    @DeleteMapping("/{id}")
    void deletePart(@PathVariable Long id){
        partRepository.deleteById(id);
    }
}
