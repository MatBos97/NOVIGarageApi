package mathijs.bos.NOVIGarageApi.Security.User;

import mathijs.bos.NOVIGarageApi.Security.Privilege.MyPrivilegeRepository;
import mathijs.bos.NOVIGarageApi.Security.Privilege.MyPrivilege;
import mathijs.bos.NOVIGarageApi.Security.Roles.Role;
import mathijs.bos.NOVIGarageApi.Security.Roles.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserRegistrationService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MyPrivilegeRepository myPrivilegeRepository;

    private final MyPrivilege readMyPrivilege = createPrivilege("READ_PRIVILEGE");
    private final MyPrivilege writeMyPrivilege = createPrivilege("WRITE_PRIVILEGE");
    private final MyPrivilege deleteMyPrivilege = createPrivilege("DELETE_PRIVILEGE");
    private final List<MyPrivilege> adminMyPrivileges = List.of(readMyPrivilege, writeMyPrivilege, deleteMyPrivilege);
    private final List<MyPrivilege> userMyPrivileges = List.of(readMyPrivilege, writeMyPrivilege);

    @Autowired
    public MyUserRegistrationService(MyPrivilegeRepository myPrivilegeRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.myPrivilegeRepository = myPrivilegeRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public User createNewAdmin(User user){
        Role adminRole = createRole("ROLE_ADMIN", adminMyPrivileges);
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
        return createRole(name, userMyPrivileges);
    }

    private Role createRole(String name, List<MyPrivilege> myPrivileges) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role(name);
                    role.setMyPrivileges(myPrivileges);
                    return roleRepository.save(role);
                });
    }

    private MyPrivilege createPrivilege(String name) {
        return myPrivilegeRepository.findByName(name)
                .orElseGet(() -> {
                    MyPrivilege myPrivilege = new MyPrivilege(name);
                    return myPrivilegeRepository.save(myPrivilege);
                });
    }
}
