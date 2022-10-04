package mathijs.bos.NOVIGarageApi.Exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(Object object) {
        super("Could not find " + object.getClass().getName().toString() + " with the given search criteria.");
    }

}
