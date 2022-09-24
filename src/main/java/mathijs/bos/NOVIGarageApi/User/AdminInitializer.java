package mathijs.bos.NOVIGarageApi.User;

import mathijs.bos.NOVIGarageApi.Security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(
                new UserEntity(
                        "Mathijs",
                        "Gaudi",
                        List.of(
                                new Role("ROLE_ADMIN")
                        )
                )
        );
    }
}
