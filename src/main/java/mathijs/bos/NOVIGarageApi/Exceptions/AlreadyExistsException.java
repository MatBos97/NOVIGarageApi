package mathijs.bos.NOVIGarageApi.Exceptions;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(Object object, String uniqueElement) {
        super("A " + object.getClass().getName().toString() + " with " + uniqueElement.toLowerCase() + " already exists.");
    }
}
