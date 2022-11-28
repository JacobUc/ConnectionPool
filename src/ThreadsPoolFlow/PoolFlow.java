package ThreadsPoolFlow;

//https://www.simplilearn.com/tutorials/java-tutorial/thread-in-java#what_is_a_thread_in_java
public class PoolFlow implements Runnable {
    
    //diferentes strings como json

    public synchronized void a(){
        System.out.println("Interrumpiendo todo");
    }

    @Override
    public void run() {
        for(int i = 0; i< 10; i++){
            if(i == 3)
                a();
            System.out.println(i);
        }
    }


    public static void main(String[] args) {
        
    }
}
