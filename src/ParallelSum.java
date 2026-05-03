public class ParallelSum { 
    public static void main(String[] args) { 
        SumTask firstHalf = new SumTask(1, 50); 
        SumTask secondHalf = new SumTask(51, 100); 
 
        Thread t1 = new Thread(firstHalf); 
        Thread t2 = new Thread(secondHalf); 
 
        t1.start(); 
        t2.start(); 
 
        try { 
            t1.join(); 
            t2.join(); 
        } catch (InterruptedException e) { 
            System.out.println("Main thread interrupted."); 
        } 
 
        int total = firstHalf.getPartialSum() + secondHalf.getPartialSum(); 
 
        System.out.println("First half sum = " + firstHalf.getPartialSum()); 
        System.out.println("Second half sum = " + secondHalf.getPartialSum()); 
        System.out.println("Total sum = " + total); 
    } 
} 

class SumTask implements Runnable { 
    private int start; 
    private int end; 
    private int partialSum; 
 
    public SumTask(int start, int end) { 
        this.start = start; 
        this.end = end; 
        this.partialSum = 0; 
    } 
 
    @Override 
    public void run() { 
        for (int i = start; i <= end; i++) { 
            partialSum += i; 
        } 
        System.out.println("Finished summing from " + start + " to " + end); 
    } 
 
    public int getPartialSum() { 
        return partialSum; 
    } 
}