package DBConnection;

public class InvalidConnectionNumberException extends Exception {
    
    public InvalidConnectionNumberException( String message ){
        super( message );
    }
}
