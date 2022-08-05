package mathijs.bos.NOVIGarageApi.Security.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserRegistrationController {


//    @Autowired
//    private RegistrationService registrationService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/All")
    List<User> all(){
        return userRepository.findAll();
    }

//    @PostMapping("/Admin")
//    User newAdmin(@RequestBody User user) {return registrationService.createNewAdmin(user);}
//
//    @PostMapping("/Mechanic")
//    User newMechanic(@RequestBody User user){
//        return registrationService.createNewMechanicUser(user);
//    }
//
//    @PostMapping("/Receptionist")
//    User newReceptionist(@RequestBody User user){
//        return registrationService.createNewReceptionistUser(user);
//    }
//
//    @PostMapping("/Office")
//    User newOffice(@RequestBody User user){
//        return registrationService.createNewOfficeUser(user);
//    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

}
