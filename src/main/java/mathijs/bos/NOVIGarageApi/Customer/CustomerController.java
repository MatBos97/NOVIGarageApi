package mathijs.bos.NOVIGarageApi.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Customer")
public class CustomerController {


    @Autowired
    private CustomerRepository customerRepository;

    @Secured({"ROLE_RECEPTIONIST", "ROLE_MECHANIC"})
    @GetMapping("/all")
    List<Customer> all(){
        return customerRepository.findAll();
    }

    @Secured({"ROLE_RECEPTIONIST", "ROLE_MECHANIC"})
    @GetMapping("/{id}")
    Customer one(@PathVariable long id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No action with id" + id + " was found."));
    }

    @Secured("ROLE_RECEPTIONIST")
    @PostMapping()
    Customer newCustomer(@RequestBody Customer customer){
        if (customerRepository.existsByFirstNameAndLastNameAllIgnoreCase(customer.getFirstName(), customer.getLastName())){
            throw new CustomerAlreadyExistsException(customer.getFirstName(), customer.getLastName());
        }
        return customerRepository.save(customer);
    }

    @Secured("ROLE_RECEPTIONIST")
    @PutMapping("/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable long id){
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setPhoneNumber(newCustomer.getPhoneNumber());
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return customerRepository.save(newCustomer);
                });
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/id")
    void deleteCustomer(@PathVariable long id){
        customerRepository.deleteById(id);
    }
}
