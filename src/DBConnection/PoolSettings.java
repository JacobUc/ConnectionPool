package DBConnection;

import org.json.simple.*;

import InterfaceReader.setConfigJSON;
import configReader.ValidarLectura;

public class PoolSettings implements setConfigJSON {

    private int initialSize;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private JSONObject configPool;
    private JSONArray poolArray;
    private int index;

    public PoolSettings(){
        readConfiguration();
    }

    @Override
    public void readConfiguration() {
        String configPath = "files/settings.json";
        ValidarLectura validacionJSON = new ValidarLectura();
        JSONObject configObject = validacionJSON.extractJSONObject(validacionJSON.readJSON(configPath));
        poolArray = validacionJSON.extractJSONArray(configObject, "connectionPool");
    }

    @Override
    public void loadConfiguration() {
        JSONObject poolObject = (JSONObject) poolArray.get(index);
        this.configPool = poolObject;
        try{
            setInitialSize( Integer.valueOf( (String)configPool.get("initialSize")) );
            setMaxTotal( Integer.valueOf( (String)configPool.get("maxTotal")) );
            setMaxIdle( Integer.valueOf( (String)configPool.get("maxIdle")) );
            setMinIdle( Integer.valueOf( (String)configPool.get("minIdle")) );
        }catch(SecurityException | NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void setIndex( int index ){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public JSONObject getConfigPool() {
        return configPool;
    }

    public void setConfigPool(JSONObject configDB) {
        this.configPool = configDB;
    }

    public JSONArray getPoolArray() {
        return poolArray;
    }

    public static void main(String[] args) {
        PoolSettings a = new PoolSettings();
        a.setIndex(0);
        a.loadConfiguration();
        System.out.println(a.getConfigPool());
    }
    
}
