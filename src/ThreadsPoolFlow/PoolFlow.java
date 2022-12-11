package ThreadsPoolFlow;

import java.sql.Connection;
import DBConnection.*;

public class PoolFlow {
    
    public static void main(String[] args) {
        DBSettings dbSettings = new DBSettings();
        dbSettings.setIndex(0);
        dbSettings.loadConfiguration();

        PoolSettings poolSettings = new PoolSettings();
        poolSettings.setIndex(0);
        poolSettings.loadConfiguration();
        poolSettings.start();

        ConnectionPool dbConnection = new ConnectionPool();
        dbConnection.setDBSettings(dbSettings);
        dbConnection.setPoolSettings(poolSettings);
        dbConnection.loadConnection();
        
        Connection connection = dbConnection.getConnection();
        Connection connection2 = dbConnection.getConnection();
        Connection connection3 = dbConnection.getConnection();
        System.out.println( "El numero de conexiones actual es: " + dbConnection.ConnectionCounter );
        System.out.println( "Numero de conexiones usadas son: " + dbConnection.ConnectionsUsed );

        while( Thread.currentThread().isAlive() ){
            if( poolSettings.getHaCambiadoJSON() ){
                dbConnection.setPoolSettings(poolSettings);
                System.out.println( "Reasignando las config del pool: " + poolSettings.getConfigPool() );
                dbConnection.closeConnection(connection);

                System.out.println( "El numero de conexiones actual es: " + dbConnection.ConnectionCounter );
                System.out.println( "Numero de conexiones usadas son: " + dbConnection.ConnectionsUsed );
                connection = dbConnection.getConnection();
            }
        }
    }
}
