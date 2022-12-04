package ThreadsPoolFlow;

public class Counter implements Runnable {
    
    @Override
    public void run() {
        for( int i = 0; i < 250; i++){
            System.out.println(i + "from Counter");
            if( i == 8){
                try {
                    Thread.interrupted();
                } catch (Exception e) {
                }
            }
        }
    }
}
