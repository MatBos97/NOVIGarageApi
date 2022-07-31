package mathijs.bos.NOVIGarageApi.Security.User;

import mathijs.bos.NOVIGarageApi.Security.Privilege.Privilege;
import mathijs.bos.NOVIGarageApi.Security.Privilege.PrivilegeRepository;
import mathijs.bos.NOVIGarageApi.Security.Roles.Role;
import mathijs.bos.NOVIGarageApi.Security.Roles.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRegistrationService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    private final Privilege readPrivilege = createPrivilege("READ_PRIVILEGE");
    private final Privilege writePrivilege = createPrivilege("WRITE_PRIVILEGE");
    private final Privilege deletePrivilege = createPrivilege("DELETE_PRIVILEGE");
    private final List<Privilege> adminPrivileges = List.of(readPrivilege, writePrivilege, deletePrivilege);
    private final List<Privilege> userPrivileges = List.of(readPrivilege, writePrivilege);

    public User createNewAdmin(User user){
        Role adminRole = createRole("ROLE_ADMIN", adminPrivileges);
        user.setRoles(List.of(adminRole));
        return userRepository.save(user);
    }

    public User createNewMechanicUser(User user) {
        Role mechanicRole = createUserRole("ROLE_MECHANIC");
        user.setRoles(List.of(mechanicRole));
        return userRepository.save(user);
    }

    public User createNewReceptionistUser(User user){
        Role receptionistRole = createUserRole("ROLE_RECEPTIONIST");
        user.setRoles(List.of(receptionistRole));
        return userRepository.save(user);
    }

    public User createNewOfficeUser(User user){
        Role officeRole = createUserRole("ROLE_OFFICE");
        user.setRoles(List.of(officeRole));
        return userRepository.save(user);
    }

    private Role createUserRole(String name){
        return createRole(name, userPrivileges);
    }

    private Role createRole(String name, List<Privilege> privileges) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role(name);
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
