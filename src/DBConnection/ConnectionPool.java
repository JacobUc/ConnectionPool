package DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool {

    private DBSettings dbSettings;
    private PoolSettings poolSettings;
    private BasicDataSource basicDataSource = null;

    public void loadConnection(){
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName( dbSettings.getDriver() );
        basicDataSource.setUsername( dbSettings.getUser() );
        basicDataSource.setPassword( dbSettings.getPassword() );
        basicDataSource.setUrl( constructURL() );
        
        basicDataSource.setInitialSize( poolSettings.getInitialSize() );
        basicDataSource.setMinIdle( poolSettings.getMinIdle() );
        basicDataSource.setMaxIdle( poolSettings.getMaxIdle() );
        basicDataSource.setMaxTotal( poolSettings.getMaxTotal() );
    }

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = this.basicDataSource.getConnection();
            JOptionPane.showMessageDialog(null, "Conexión establecida con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public boolean closeConnection(Connection connection){
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------- METODOS IMPLEMENTADOS A PARTIR DEL DBSETTINGS y POOLSETTINGS
    public void setDBSettings(DBSettings dbSettings){
        this.dbSettings = dbSettings;
    }

    public void setPoolSettings(PoolSettings poolSettings){
        this.poolSettings = poolSettings;
    }

    public String constructURL(){
        //"jdbc:mariadb://localhost:3306/database_name"
        String url = "jdbc:" + 
            dbSettings.getSGBD() + "://" +
            dbSettings.getHost() + ":" + 
            dbSettings.getPort() + "/" +
            dbSettings.getNameDB() + 
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ;
        return url;
    }

}