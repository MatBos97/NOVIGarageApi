package mathijs.bos.NOVIGarageApi.User;

import mathijs.bos.NOVIGarageApi.Security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;

@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/User")
public class UserController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/All")
    List<UserEntity> all(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    UserEntity getUser(@PathVariable long id){
        return userRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping()
    UserEntity newUser(@RequestBody UserEntity newUser){
        return userService.newUser(newUser);
    }

    @PutMapping("/{id}")
    UserEntity replaceUser(@RequestBody UserEntity newUserEntity, @PathVariable long id){
        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setUsername(newUserEntity.getUsername());
                    userEntity.setPassword(newUserEntity.getPassword());
                    userEntity.setRoles(newUserEntity.getRoles());
                    return userRepository.save(userEntity);
                }).orElseGet(() -> {
                    newUserEntity.setId(id);
                    return userRepository.save(newUserEntity);
                });
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable long id){
        userRepository.deleteById(id);
    }
}
