package PoolConnection;

import org.json.simple.*;

import InterfaceReader.setConfigJSON;
import configReader.ValidarLectura;

public class PoolSettings extends Thread implements setConfigJSON {
    private int initialSize;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private JSONObject configPool;
    private JSONArray poolArray;
    private int index;
    private boolean haCambiado;

    public PoolSettings(){
        readConfiguration();
    }

    @Override
    public synchronized void readConfiguration() {
        String configPath = "files/settings.json";
        ValidarLectura validacionJSON = new ValidarLectura();
        JSONObject configObject = validacionJSON.extractJSONObject(validacionJSON.readJSON(configPath));
        poolArray = validacionJSON.extractJSONArray(configObject, "connectionPool");
    }

    @Override
    public synchronized void loadConfiguration() {
        PoolSettingsValidator poolSettingsValidator = new PoolSettingsValidator();
        JSONObject poolObject = (JSONObject) poolArray.get(index);
        this.configPool = poolObject;

        try{
            int initialSize = Integer.valueOf( (String)configPool.get("initialSize"));
            int maxTotal = Integer.valueOf( (String)configPool.get("maxTotal"));
            int maxIdle = Integer.valueOf( (String)configPool.get("maxIdle"));
            int minIdle = Integer.valueOf( (String)configPool.get("minIdle"));
            boolean areSettingsValid =  poolSettingsValidator.validateSizes( initialSize, maxTotal, maxIdle, minIdle );

            if( !areSettingsValid ){
                return;
            }

            setInitialSize( initialSize );
            setMaxTotal( maxTotal );
            setMaxIdle( maxIdle );
            setMinIdle( minIdle );

        }catch( NumberFormatException e){  }
    }

    @Override
    public void run() {

        JSONObject poolSettings = getConfigPool();
        JSONObject newPoolSettings = new JSONObject();

        while( Thread.currentThread().isAlive() ){
            readConfiguration();
            loadConfiguration();
            newPoolSettings = getConfigPool();
            boolean isSameSettings = poolSettings.equals(newPoolSettings);
            if( !isSameSettings ){
                System.out.println("--- NO son lo mismo ---");
                haCambiado = true;
                poolSettings = newPoolSettings;
            }else{
                System.out.println("--- Son lo mismo ---");
            }
            haCambiado = false;
            try { Thread.sleep( 3000 ); } 
            catch (InterruptedException e) { }
        }
    }

    public boolean getHaCambiadoJSON(){
        return this.haCambiado;
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
}
