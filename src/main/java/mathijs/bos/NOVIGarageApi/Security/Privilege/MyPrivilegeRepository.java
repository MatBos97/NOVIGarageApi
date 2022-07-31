package mathijs.bos.NOVIGarageApi.Security.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyPrivilegeRepository extends JpaRepository<MyPrivilege, Long> {
    Optional<MyPrivilege> findByName(String name);

}