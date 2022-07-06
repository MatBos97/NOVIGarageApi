package mathijs.bos.NOVIGarageApi.Security.User;

import mathijs.bos.NOVIGarageApi.Security.Privilege.Privilege;
import mathijs.bos.NOVIGarageApi.Security.Privilege.PrivilegeRepository;
import mathijs.bos.NOVIGarageApi.Security.Roles.Role;
import mathijs.bos.NOVIGarageApi.Security.Roles.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserRegistrationController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @PostMapping
    User newUser(@RequestBody User user){
        return userRepository.findByUsername(user.getUsername())
                .orElseGet(()->{
                    user.setRoles(createRolesAndPrivileges(user.getRoles()));
                    return userRepository.save(user);
                });

    }

    private Collection<Role> createRolesAndPrivileges(Collection<Role> roles){
        Collection<Role> userRoles = new ArrayList<>();
        for (Role role : roles){
            userRoles.add(createRole(role));
        }
        return userRoles;
    }

    private Role createRole(Role role) {
        return roleRepository.findByName(role.getName())
                .orElseGet(() -> {
                    Collection<Privilege> privileges = new ArrayList<>();
                    for (Privilege privilege : role.getPrivileges()){
                        privileges.add(createPrivilege(privilege.getName()));
                    }
                    role.setPrivileges(privileges);
                    return roleRepository.save(role);
                });
    }

    private Privilege createPrivilege(String name) {
        return privilegeRepository.findByName(name)
                .orElseGet(() -> {
                    Privilege privilege = new Privilege(name);
                    return privilegeRepository.save(privilege);
                });
    }
}
