package frc.lightning.util;

import java.util.LinkedList;
import java.util.List;

public class SimpleSchedule {
    private int counter = 0;
    private int frequency = 1;
    private List<Runnable> queue = new LinkedList<>();

    public SimpleSchedule(int frequency) {
        this.frequency = frequency;
    }

    public void period() {
        counter += 1;

        if (counter % frequency == 0) {
            for (var fn : queue) {
                fn.run();
            }
        }
    }

    public void schedule(Runnable fn) {
        queue.add(fn);
    }
}
