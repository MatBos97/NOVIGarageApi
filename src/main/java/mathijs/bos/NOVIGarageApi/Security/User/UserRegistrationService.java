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


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    private final List<Privilege> adminPrivileges;
    private final List<Privilege> userPrivileges;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;

        Privilege readPrivilege = createPrivilege("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilege("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilege("DELETE_PRIVILEGE");

        adminPrivileges = List.of(readPrivilege, writePrivilege, deletePrivilege);
        userPrivileges = List.of(readPrivilege, writePrivilege);
    }


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
