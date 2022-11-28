package ThreadsPoolFlow;

public class Monitor implements Runnable {
    
    int number;

    public Monitor(int number){
        this.number = number;
    }

    @Override
    public void run() {
        for(int i = 0; i < 4; i++){
            System.out.println(i + "from " + this.number);
        }
    }

    //Leemos el archivo cada 1 seg
    public void leerArchivo(){
        
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread( new Monitor(1) );
        Thread thread2 = new Thread( new Monitor(2) );
        Thread thread3 = new Thread( new Monitor(3) );

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
