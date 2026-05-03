public class TwoTasks { 
    public static void main(String[] args) { 
        Runnable numbersTask = new NumberTask(); 
        Runnable lettersTask = new LetterTask(); 
 
        Thread t1 = new Thread(numbersTask); 
        Thread t2 = new Thread(lettersTask); 
 
        System.out.println("Main: starting both threads"); 
        t1.start(); 
        t2.start(); 
 
        try { 
            t1.join(); 
            t2.join(); 
        } catch (InterruptedException e) { 
            System.out.println("Main thread interrupted."); 
        } 
 
        System.out.println("Main: both threads are done"); 
    } 
} 
 
class NumberTask implements Runnable { 
    @Override 
    public void run() { 
        for (int i = 1; i <= 5; i++) { 
            System.out.println("Number thread: " + i); 
            try { 
                Thread.sleep(200); 
            } catch (InterruptedException e) { 
                System.out.println("Number thread interrupted."); 
            } 
        } 
    } 
} 
 
class LetterTask implements Runnable { 
    @Override 
    public void run() { 
        for (char c = 'A'; c <= 'E'; c++) { 
            System.out.println("Letter thread: " + c); 
            try { 
                Thread.sleep(200); 
            } catch (InterruptedException e) { 
                System.out.println("Letter thread interrupted."); 
            } 
        } 
    } 
}