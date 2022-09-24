package mathijs.bos.NOVIGarageApi.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RoleService {


    @Autowired
    private RoleRepository roleRepository;

    public Collection<Role> NewRoles(Collection<Role> newRoles){
        List<Role> savedRoles = new ArrayList<>();

        for(Role newRole : newRoles){

            Role r = roleRepository.findByRoleNameIgnoreCase(newRole.getRoleName())
                    .orElseGet(() -> roleRepository.save(newRole));

            savedRoles.add(r);
        }
        return savedRoles;
    }
}
