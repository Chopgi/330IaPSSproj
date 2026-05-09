import java.util.ArrayList;
import java.util.List;

public class SimLogger {
    private final List<String> entries = new ArrayList<>();
    private final long start = System.currentTimeMillis();

    public synchronized void log(String message) {
        long t = System.currentTimeMillis() - start;
        String entry = String.format("[%5d ms] %s", t, message);
        entries.add(entry);
        System.out.println(entry);
    }

    public void print() {
        entries.forEach(System.out::println);
    }
}
