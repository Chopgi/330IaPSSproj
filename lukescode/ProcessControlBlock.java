// Replaces ParallelSum.java — rename/replace with ProcessControlBlock.java
public class ProcessControlBlock {
    private static int idCounter = 1;

    public final int pid;
    public final String name;
    public volatile ProcessState state;
    public int burstTimeRemaining;   // CPU time-slices still needed
    public final int ioBurstAt;      // trigger I/O when bursts remaining == this
    private boolean ioTriggered = false;

    public ProcessControlBlock(String name, int totalBursts, int ioBurstAt) {
        this.pid                = idCounter++;
        this.name               = name;
        this.state              = ProcessState.NEW;
        this.burstTimeRemaining = totalBursts;
        this.ioBurstAt          = ioBurstAt;
    }

    public boolean shouldTriggerIO() {
        return !ioTriggered && burstTimeRemaining == ioBurstAt;
    }

    public void markIOTriggered() { ioTriggered = true; }

    @Override
    public String toString() {
        return String.format("P%d(%s)", pid, name);
    }
}
