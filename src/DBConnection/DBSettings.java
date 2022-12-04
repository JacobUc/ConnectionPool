package DBConnection;

import org.json.simple.*;
import InterfaceReader.setConfigJSON;
import configReader.ValidarLectura;

public class DBSettings implements setConfigJSON{

    private String SGBD;
    private String nameDB;
    private String host;
    private String port;
    private String user;
    private String password;
    private JSONObject configDB;
    private String driver;
    private JSONArray dbArray;
    private int index;

    public DBSettings(){
        readConfiguration();
    }

    @Override
    public void readConfiguration() {
        String configPath = "files/settings.json";
        ValidarLectura validacionJSON = new ValidarLectura();
        JSONObject configObject = validacionJSON.extractJSONObject(validacionJSON.readJSON(configPath));
        dbArray = validacionJSON.extractJSONArray(configObject, "databaseAccess");
    }

    @Override
    public synchronized void loadConfiguration() {
        JSONObject configDBObject = (JSONObject) dbArray.get(this.index);
        this.configDB = configDBObject;
        try{
            setSGBD((String)configDB.get("dbms"));
            setHost((String)configDB.get("host"));
            setNameDB((String)configDB.get("databaseName"));
            setPassword((String)configDB.get("password"));
            setUser((String)configDB.get("user"));
            setPort((String)configDB.get("port"));
            setDriver((String)configDB.get("driver"));
            // System.out.println("Durmiendo un ratito mientras reajustamos");
            // Thread.currentThread().sleep(8000);
        }catch( SecurityException | NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

    public String getDriver() {
        return driver;
    }
  
    public void setDriver(String driver) {
        this.driver = driver;
    }
  
    public String getSGBD() {
        return SGBD;
    }
  
    public void setSGBD(String sGBD) {
        SGBD = sGBD;
    }
  
    public String getHost() {
        return host;
    }
  
    public void setHost(String host) {
        this.host = host;
    }
  
    public String getNameDB() {
        return nameDB;
    }
  
    public void setNameDB(String nameDB) {
        this.nameDB = nameDB;
    }
  
    public String getPort() {
        return port;
    }
  
    public void setPort(String port) {
        this.port = port;
    }
  
    public JSONObject getConfigDB() {
        return configDB;
    }
  
    public void setConfigDB(JSONObject configDB) {
        this.configDB = configDB;
    }
  
    public String getUser() {
        return user;
    }
  
    public void setUser(String user) {
        this.user = user;
    }
  
    public String getPassword() {
        return password;
    }
  
    public void setPassword(String password) {
        this.password = password;
    }

}
