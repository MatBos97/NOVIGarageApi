package mathijs.bos.NOVIGarageApi.Security.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MyRoleHierarchy {

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_MECHANIC \n ROLE_RECEPTIONIST";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


}
