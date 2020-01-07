package frc.lightning.util;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.Notifier;

public class ThreadScheduler {
    private LinkedList<Runnable> queue = new LinkedList<>();
    private Notifier scheduler;
    private int loopSpeed;

    public ThreadScheduler(int loopSpeed) {
        this.loopSpeed = loopSpeed;

        scheduler = new Notifier(() -> {
            synchronized(this) {
                for (var fn : queue) {
                    fn.run();
                }
            }
        });
    }

    public void add(Runnable fn) {
        synchronized(this) {
            queue.add(fn);
        }
    }

    public void stop() {
        scheduler.stop();
    }

    public void start() {
        scheduler.startPeriodic(loopSpeed);
    }
}