public class ThreadDemo { 
    static class Worker implements Runnable { 
        private final String name; 
 
        Worker(String name) { 
            this.name = name; 
        } 
 
        @Override 
        public void run() { 
            for (int i = 1; i <= 3; i++) { 
                System.out.println(name + " " + i); 
            } 
        } 
    } 
 
    public static void main(String[] args) throws InterruptedException { 
        Thread t1 = new Thread(new Worker("A")); 
        Thread t2 = new Thread(new Worker("B")); 
 
        t1.start(); 
        t2.start(); 
 
        t1.join(); 
        t2.join(); 
 
        System.out.println("Done"); 
    } 
} 