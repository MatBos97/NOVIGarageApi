package mathijs.bos.NOVIGarageApi.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/All")
    List<User> all(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable long id){
        return userRepository.findById(id)
                .orElseThrow();
    }

    @PostMapping()
    User newUser(@RequestBody User newUser){
        return userRepository.findById(newUser.getId())
                .orElseGet(() -> userRepository.save(newUser));
    }

    @PutMapping("/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setRoles(newUser.getRoles());
                    return userRepository.save(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable long id){
        userRepository.deleteById(id);
    }
}
