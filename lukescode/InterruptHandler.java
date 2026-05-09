// Replaces TwoTasks.java — rename/replace with InterruptHandler.java
import java.util.Queue;

public class InterruptHandler {

    // ── Timer interrupt ──────────────────────────────────────────────────────
    // Called when a running process exhausts its time-slice.
    // Preempts the running process back to READY.
    public static void handleTimerInterrupt(ProcessControlBlock running,
                                            Queue<ProcessControlBlock> readyQueue,
                                            SimLogger log) {
        if (running == null) return;
        log.log("  [TIMER INTERRUPT] Preempting " + running +
                " — time-slice expired → READY");
        running.state = ProcessState.READY;
        readyQueue.add(running);
    }

    // ── I/O request interrupt ────────────────────────────────────────────────
    // Called when a running process issues an I/O request.
    // Moves it from RUNNING → WAITING.
    public static void handleIORequest(ProcessControlBlock proc,
                                       Queue<ProcessControlBlock> waitingQueue,
                                       SimLogger log) {
        log.log("  [I/O REQUEST] " + proc + " requests I/O → WAITING");
        proc.state = ProcessState.WAITING;
        proc.markIOTriggered();
        waitingQueue.add(proc);
    }

    // ── I/O completion interrupt ─────────────────────────────────────────────
    // Runs asynchronously on a worker thread; after a delay the process wakes
    // and is moved back to READY (simulating an interrupt from the I/O device).
    public static Runnable ioCompletionInterrupt(ProcessControlBlock proc,
                                                  Queue<ProcessControlBlock> readyQueue,
                                                  SimLogger log,
                                                  int delayMs) {
        return () -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.log("  [I/O COMPLETE] " + proc + " finished I/O → READY");
            proc.state = ProcessState.READY;
            readyQueue.add(proc);
        };
    }
}
