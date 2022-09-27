package mathijs.bos.NOVIGarageApi.Exceptions;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String entity, String memberVariable, String valueOfVariable) {
        super("A " + entity + " with " + memberVariable + " of " + valueOfVariable + " already exists.");
    }
}
