package DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionPool {
    private DBSettings dbSettings;
    private PoolSettings poolSettings;
    private BasicDataSource basicDataSource;
    private ArrayList<Connection> connections;
    public static int ConnectionCounter = 0;
    public static int ConnectionsUsed = 0;

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

        initilizeConnections();
    }

    private void initilizeConnections(){
        this.connections = new ArrayList<>();

        for( int i = 0; i < basicDataSource.getInitialSize(); i++ ){
            this.connections.add( createConnection() );
            ConnectionCounter++;
        }
    }

    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = this.basicDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection(){
        Connection connection = null;
        try {

            if( ConnectionCounter >= basicDataSource.getMaxTotal() ){
                throw new InvalidConnectionNumberException("El numero de conexiones es insuficiente");
            }

            if( ConnectionsUsed < basicDataSource.getInitialSize() ){
                ConnectionsUsed++;
                JOptionPane.showMessageDialog(null, "Conexión establecida con éxito");
                return this.connections.get( ConnectionsUsed - 1 );
            }

            if( ConnectionCounter < basicDataSource.getMaxTotal() ){
                ConnectionCounter++;
                ConnectionsUsed++;
                this.connections.add( createConnection() );
                JOptionPane.showMessageDialog(null, "Conexión establecida con éxito");
                return this.connections.get( ConnectionsUsed - 1 );
            }

        } catch ( InvalidConnectionNumberException e) { }

        return connection;
    }

    public boolean closeConnection(Connection connection){
        //Realizamos la busqueda de la conexion a cerrar y la eliminamos del arreglo, si es que cambiaron los tamanos
        int indexACerrar = -1;
        try {

            for( Connection con : connections ){
                if( con.equals( connection ) ){
                    indexACerrar = connections.indexOf(con);
                }
            }

            if( indexACerrar == -1 )
                return false;
            
            connections.get(indexACerrar).close();
            ConnectionsUsed--;

            if( ConnectionCounter > basicDataSource.getInitialSize() ){
                this.connections.remove( indexACerrar );
                ConnectionCounter--;
            }
            
            return true;

        } catch (SQLException e) { return false; }
    }

    public void setDBSettings(DBSettings dbSettings){
        this.dbSettings = dbSettings;
    }

    public void setPoolSettings(PoolSettings poolSettings){
        this.poolSettings = poolSettings;
    }

    public PoolSettings getPoolSettings(){
        return this.poolSettings;
    }

    private String constructURL(){
        String url = "jdbc:" + 
            dbSettings.getSGBD() + "://" +
            dbSettings.getHost() + ":" + 
            dbSettings.getPort() + "/" +
            dbSettings.getNameDB() + 
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ;
        return url;
    }
}
