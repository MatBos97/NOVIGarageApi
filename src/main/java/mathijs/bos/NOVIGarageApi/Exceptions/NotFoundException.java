package mathijs.bos.NOVIGarageApi.Exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String entity, String searchTag, String searchVariable) {
        super("No " + entity + " was found with " + searchTag + ": " + searchVariable);
    }

}
