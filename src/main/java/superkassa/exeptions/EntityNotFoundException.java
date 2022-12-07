package superkassa.exeptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super("Entity with " + message + " not found.");
    }
}
