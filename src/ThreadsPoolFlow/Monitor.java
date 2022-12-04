package ThreadsPoolFlow;

import org.json.simple.JSONObject;

import DBConnection.DBSettings;

public class Monitor implements Runnable {

    private DBSettings dbSettings;
    private int index;

    public void setIndex( int index ){
        this.index = index;
    }

    
    @Override
    public void run() {

        dbSettings = new DBSettings();
        //no olvidar setear el index
        dbSettings.setIndex(0);
        dbSettings.loadConfiguration();
        JSONObject currentDBSettings = dbSettings.getConfigDB();
        JSONObject newDBSettings = new JSONObject();

        while( Thread.currentThread().isAlive() ){
            dbSettings.readConfiguration();
            dbSettings.loadConfiguration();
            newDBSettings = dbSettings.getConfigDB();

            boolean isSameSettings = currentDBSettings.equals(newDBSettings);

            if( isSameSettings ){
                //si no son lo mismo, cambiamos las settings
                System.out.println("--- Son lo mismo ---");
                
                //dbSettings.setConfigDB(newDBSettings);
                //dbSettings.loadConfiguration();
                // notifyAll();
            }

            // try {
            //     System.out.println("Durmiendo");
            //     Thread.sleep(2000);
            // } catch (Exception e) {}
        }
        
    }

    
    public static void main(String[] args) {
        Thread thread1 = new Thread( new Monitor() );
        // Thread thread2 = new Thread( new Counter() );
        thread1.start();
        try {
            thread1.wait();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        // thread2.start();
    }
}
