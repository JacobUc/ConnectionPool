package ThreadsPoolFlow;

import java.sql.Connection;
import DBConnection.*;

public class PoolFlow {
    
    public static void main(String[] args) {
        DBSettings dbSettings = new DBSettings();
        dbSettings.setIndex(0);
        dbSettings.loadConfiguration();

        PoolSettingsThread poolSettings = new PoolSettingsThread();
        poolSettings.setIndex(0);
        poolSettings.loadConfiguration();
        poolSettings.start();

        ConnectionPoolThread dbConnection = new ConnectionPoolThread();
        dbConnection.setDBSettings(dbSettings);
        dbConnection.setPoolSettings(poolSettings);
        dbConnection.loadConnection();
        
        Connection connection = dbConnection.getConnection();

        while( Thread.currentThread().isAlive() ){

            if( poolSettings.getHaCambiadoJSON() ){
                dbConnection.setPoolSettings(poolSettings);
                System.out.println( "Reasignando las config del pool: " + poolSettings.getConfigPool() );
                System.out.println( "El valor actual de InitialSize es: " + dbConnection.getPoolSettings().getInitialSize() );
                dbConnection.closeConnection(connection);
                connection = dbConnection.getConnection();
            }
        }
        
    }
}
