// Replaces ThreadDemo.java — rename/replace with Scheduler.java
import java.util.*;
import java.util.concurrent.*;

public class Scheduler {
    private static final int TIME_SLICE_MS = 300;  // one CPU time-slice duration
    private static final int IO_DELAY_MS   = 800;  // simulated I/O completion delay

    private final Queue<ProcessControlBlock> readyQueue   = new LinkedList<>();
    private final Queue<ProcessControlBlock> waitingQueue = new LinkedList<>();
    private final SimLogger log;
    private final ExecutorService ioPool = Executors.newCachedThreadPool();

    public Scheduler(SimLogger log) {
        this.log = log;
    }

    // Admit a newly created process into the ready queue (NEW → READY)
    public void admit(ProcessControlBlock pcb) {
        log.log(pcb + " NEW → READY");
        pcb.state = ProcessState.READY;
        readyQueue.add(pcb);
    }

    // Run the scheduler loop until all processes are terminated
    public void run() throws InterruptedException {
        log.log("\n=== Scheduler started (Round-Robin, slice=" +
                TIME_SLICE_MS + "ms) ===\n");

        while (!readyQueue.isEmpty() || !waitingQueue.isEmpty()) {

            if (readyQueue.isEmpty()) {
                // CPU is idle — waiting for an I/O completion interrupt
                log.log("  [CPU IDLE] waiting for I/O completion interrupt...");
                Thread.sleep(100);
                continue;
            }

            // Dispatch next process from ready queue
            ProcessControlBlock running = readyQueue.poll();
            log.log(running + " READY → RUNNING  (bursts left: " +
                    running.burstTimeRemaining + ")");
            running.state = ProcessState.RUNNING;

            // Simulate one time-slice of CPU work
            Thread.sleep(TIME_SLICE_MS);
            running.burstTimeRemaining--;

            if (running.shouldTriggerIO()) {
                // Process requests I/O mid-execution
                InterruptHandler.handleIORequest(running, waitingQueue, log);
                // Fire async I/O completion interrupt on a background worker thread
                ioPool.submit(InterruptHandler.ioCompletionInterrupt(
                        running, readyQueue, log, IO_DELAY_MS));

            } else if (running.burstTimeRemaining <= 0) {
                // Process finished all CPU work
                log.log(running + " RUNNING → TERMINATED");
                running.state = ProcessState.TERMINATED;

            } else {
                // Timer interrupt — preempt back to ready queue
                InterruptHandler.handleTimerInterrupt(running, readyQueue, log);
            }
        }

        ioPool.shutdown();
        ioPool.awaitTermination(5, TimeUnit.SECONDS);
        log.log("\n=== All processes terminated ===");
    }
}
