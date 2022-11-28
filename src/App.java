import java.sql.Connection;

import DBConnection.ConnectionPool;
import DBConnection.DBSettings;
import DBConnection.PoolSettings;

public class App {

    public static void main(String[] args) {
        DBSettings dbSettings = new DBSettings();
        dbSettings.setIndex(0);
        dbSettings.loadConfiguration();

        PoolSettings poolSettings = new PoolSettings();
        poolSettings.setIndex(0);
        poolSettings.loadConfiguration();

        ConnectionPool dbConnection = new ConnectionPool();
        dbConnection.setDBSettings(dbSettings);
        dbConnection.setPoolSettings(poolSettings);
        dbConnection.loadConnection();
        Connection connection = dbConnection.getConnection();
    }

}
