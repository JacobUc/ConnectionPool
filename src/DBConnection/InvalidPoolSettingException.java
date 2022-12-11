package DBConnection;

public class InvalidPoolSettingException extends Exception {
    
    public InvalidPoolSettingException( String message ){
        super( message );
    }
}
