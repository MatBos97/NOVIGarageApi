package mathijs.bos.NOVIGarageApi.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
}