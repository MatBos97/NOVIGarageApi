package mathijs.bos.NOVIGarageApi.Car;

public class CarAlreadyExistsException extends RuntimeException{

    public CarAlreadyExistsException(String numberplate) {
        super("Car with numberplate: " + numberplate + " already exists");
    }
}
