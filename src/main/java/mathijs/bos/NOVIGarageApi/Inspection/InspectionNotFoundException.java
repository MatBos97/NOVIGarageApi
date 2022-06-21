package mathijs.bos.NOVIGarageApi.Inspection;

public class InspectionNotFoundException extends RuntimeException{

    public InspectionNotFoundException(Long id) {
        super("Inspection with id: " + id + " was not found.");
    }
}
