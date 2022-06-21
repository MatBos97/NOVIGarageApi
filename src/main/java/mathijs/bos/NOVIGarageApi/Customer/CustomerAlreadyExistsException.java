package mathijs.bos.NOVIGarageApi.Customer;

public class CustomerAlreadyExistsException extends RuntimeException{

    public CustomerAlreadyExistsException(String firstName, String lastName) {
        super("Customer with name: " + firstName + " " + lastName + " already exists.");
    }
}
