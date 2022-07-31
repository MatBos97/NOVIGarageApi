package mathijs.bos.NOVIGarageApi.Security;

import mathijs.bos.NOVIGarageApi.Security.Privilege.Privilege;
import mathijs.bos.NOVIGarageApi.Security.Privilege.PrivilegeRepository;
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
    private PrivilegeRepository privilegeRepository;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        Privilege readPrivilege = createPrivilege("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilege("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilege("DELETE_PRIVILEGE");
        List<Privilege> adminPrivileges = List.of(readPrivilege, writePrivilege, deletePrivilege);

        Role adminRole = createRole("ROLE_ADMIN", adminPrivileges);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername("admin");
        user.setPassword(encoder.encode("admin"));
        user.setRoles(List.of(adminRole));
        userRepository.save(user);

        alreadySetup = true;
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
