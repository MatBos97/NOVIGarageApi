package mathijs.bos.NOVIGarageApi.User;

import mathijs.bos.NOVIGarageApi.Security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;


    public UserEntity newUser(UserEntity newUser){
        return userRepository.findByUsernameIgnoreCase(newUser.getUsername())
                .orElseGet(() -> {
                    newUser.setRoles(roleService.NewRoles(newUser.getRoles()));
                    return userRepository.save(newUser);
                });
    }
}
