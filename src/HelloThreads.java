public class HelloThreads { 
    public static void main(String[] args) { 
        Runnable task = new MessageTask("Worker thread", 5); 
 
        Thread worker = new Thread(task); 
 
        System.out.println("Main: starting worker"); 
        worker.start(); 
 
        System.out.println("Main: waiting for worker to finish"); 
 
        try { 
            worker.join(); 
        } catch (InterruptedException e) { 
            System.out.println("Main thread was interrupted."); 
        } 
 
        System.out.println("Main: program finished"); 
    } 
} 
 
class MessageTask implements Runnable { 
    private String message; 
    private int times; 

    public MessageTask(String message, int times) {
                this.message = message; 
        this.times = times; 
    } 
 
    @Override 
    public void run() { 
        for (int i = 1; i <= times; i++) { 
            System.out.println(message + " says hello #" + i); 
 
            try { 
                Thread.sleep(300); 
            } catch (InterruptedException e) { 
                System.out.println("Worker thread was interrupted."); 
            } 
        } 
    } 
}