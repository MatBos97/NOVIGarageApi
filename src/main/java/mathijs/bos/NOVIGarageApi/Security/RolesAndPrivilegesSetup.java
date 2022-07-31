package mathijs.bos.NOVIGarageApi.Security;

import mathijs.bos.NOVIGarageApi.Security.Privilege.MyPrivilege;
import mathijs.bos.NOVIGarageApi.Security.Privilege.MyPrivilegeRepository;
import mathijs.bos.NOVIGarageApi.Security.Roles.Role;
import mathijs.bos.NOVIGarageApi.Security.Roles.RoleRepository;
import mathijs.bos.NOVIGarageApi.Security.User.User;
import mathijs.bos.NOVIGarageApi.Security.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesAndPrivilegesSetup implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MyPrivilegeRepository myPrivilegeRepository;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        MyPrivilege readMyPrivilege = createPrivilege("READ_PRIVILEGE");
        MyPrivilege writeMyPrivilege = createPrivilege("WRITE_PRIVILEGE");
        MyPrivilege deleteMyPrivilege = createPrivilege("DELETE_PRIVILEGE");
        List<MyPrivilege> adminMyPrivileges = List.of(readMyPrivilege, writeMyPrivilege, deleteMyPrivilege);

        Role adminRole = createRole("ROLE_ADMIN", adminMyPrivileges);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("admin"));
        user.setRoles(List.of(adminRole));
        userRepository.save(user);

        alreadySetup = true;
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
