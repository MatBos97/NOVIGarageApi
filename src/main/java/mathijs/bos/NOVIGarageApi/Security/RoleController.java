package mathijs.bos.NOVIGarageApi.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Role")
public class RoleController {


    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/All")
    List<Role> all(){
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    Role findRole(@PathVariable long id){
        return roleRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping()
    Role newRole(@RequestBody Role newRole){
        return roleRepository.save(newRole);
    }

    @PutMapping("/{id}")
    Role replaceRole(@PathVariable long id, @RequestBody Role newRole){
        return roleRepository.findById(id)
                .map(role -> {
                    role.setRoleName(newRole.getRoleName());
                    return roleRepository.save(role);
                }).orElseGet(()->{
                    newRole.setId(id);
                    return roleRepository.save(newRole);
                });
    }

    @DeleteMapping("/{id}")
    void deleteRole(@PathVariable long id){
        roleRepository.deleteById(id);
    }
}
