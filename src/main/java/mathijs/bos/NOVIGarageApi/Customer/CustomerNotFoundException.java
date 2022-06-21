package mathijs.bos.NOVIGarageApi.Customer;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(long id) {
        super("Can not found customer with id: " + id);
    }
}
