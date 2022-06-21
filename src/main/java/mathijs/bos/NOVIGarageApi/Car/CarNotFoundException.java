package mathijs.bos.NOVIGarageApi.Car;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(long id) {
        super("Could not find car with id: " + id);
    }
}
