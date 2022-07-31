package mathijs.bos.NOVIGarageApi.Security.User;

import mathijs.bos.NOVIGarageApi.Security.Privilege.MyPrivilege;
import mathijs.bos.NOVIGarageApi.Security.Roles.Role;
import mathijs.bos.NOVIGarageApi.Security.Roles.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImplementation implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseGet(() -> new User("", ""));

        //todo: I am pretty sure that I can improve this. Also, I don't want anyone to be able to access anything so I might have to throw a exception here.
        //      More testing is required.


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, true,
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> role_user) {
        return getGrantedAuthorities(getPrivileges(role_user));
    }

    private List<String> getPrivileges(Collection<Role> role_user) {
        List<String> privileges = new ArrayList<>();
        List<MyPrivilege> collection = new ArrayList<>();
        for (Role role : role_user){
            privileges.add(role.getName());
            collection.addAll(role.getMyPrivileges());
        }
        for (MyPrivilege item : collection){
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges){
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
