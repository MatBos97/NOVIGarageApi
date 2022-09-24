package mathijs.bos.NOVIGarageApi.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public RoleHierarchyImpl roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_ADMIN > ROLE_RECEPTIONIST and ROLE_ADMIN > ROLE_MECHANIC and ROLE_ADMIN > ROLE_CASHIER"
        );
        return roleHierarchy;
    }
}
