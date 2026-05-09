// App.java — main simulation entry point
public class App {
    public static void main(String[] args) throws Exception {

        SimLogger log = new SimLogger();

        // ── Create processes ─────────────────────────────────────────────────
        // ProcessControlBlock(name, totalBursts, ioBurstAt)
        //   totalBursts : total CPU time-slices the process needs
        //   ioBurstAt   : issues an I/O request when burstTimeRemaining == this value
        //                 (set to 99 or higher than totalBursts to skip I/O)
        ProcessControlBlock p1 = new ProcessControlBlock("TextEdit",  4, 2);   // does I/O
        ProcessControlBlock p2 = new ProcessControlBlock("Browser",   3, 99);  // no I/O
        ProcessControlBlock p3 = new ProcessControlBlock("FileSync",  5, 3);   // does I/O
        ProcessControlBlock p4 = new ProcessControlBlock("Antivirus", 2, 99);  // no I/O

        Scheduler scheduler = new Scheduler(log);

        // Admit all processes (NEW → READY)
        scheduler.admit(p1);
        scheduler.admit(p2);
        scheduler.admit(p3);
        scheduler.admit(p4);

        // Run the simulation
        scheduler.run();

        // Print the full timestamped event timeline
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║         PROCESS-STATE SIMULATION LOG         ║");
        System.out.println("╚══════════════════════════════════════════════╝\n");
        log.print();
    }
}
