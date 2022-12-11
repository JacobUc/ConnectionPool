package PoolConnection;

public class PoolSettingsValidator {

    public boolean validateSizes( int initialSize, int maxSize, int maxIdle, int minIdle ){

        try {
            if( initialSize <= 0 || maxSize <= 0 || maxIdle <= 0 || minIdle <= 0 ){
                throw new InvalidPoolSettingException("No se permiten valores negativos");
            }
    
            if( initialSize > maxSize ){
                throw new InvalidPoolSettingException("El tamano inicial debe ser menor al tamano maximo");
            }
    
            if( maxIdle > maxSize ){
                throw new InvalidPoolSettingException("El valor de maxIdle debe ser menor al tamano maximo");
            }
    
            if( minIdle > maxIdle || minIdle > maxSize ){
                throw new InvalidPoolSettingException("El valor de minIdle debe ser menor al tamano maximo y menor al maxIdle");
            }
            
        } catch ( InvalidPoolSettingException e ) { e.printStackTrace(); return false; }
        
        return true;
    }
    

}
