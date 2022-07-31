package mathijs.bos.NOVIGarageApi.Security.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserRegistrationController {


    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/All")
    List<User> all(){
        return userRepository.findAll();
    }

    @PostMapping("/Mechanic")
    User newMechanic(@RequestBody User user){
        return userRegistrationService.createNewMechanicUser(user);
    }

    @PostMapping("/Receptionist")
    User newReceptionist(@RequestBody User user){
        return userRegistrationService.createNewReceptionistUser(user);
    }

    @PostMapping("/Office")
    User newOffice(@RequestBody User user){
        return userRegistrationService.createNewOfficeUser(user);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

}
