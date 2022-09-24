package mathijs.bos.NOVIGarageApi.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByUsernameIgnoreCase(String username);

}