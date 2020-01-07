package frc.lightning.util;

import edu.wpi.first.wpilibj.Timer;

public class LightningTimer {
    private double startedAt;
    private double stoppedAt;
    private boolean stopped;

    public LightningTimer() {
        stopped = false;
        reset();
    }

    public double get() {
        if (stopped) {
            return stoppedAt - startedAt;
        } else {
            return Timer.getFPGATimestamp() - startedAt;
        }
    }

    public boolean hasPeriodPassed(double time) {
        return get() > time;
    }

    public void reset() {
        if (stopped)
            startedAt = stoppedAt;
        else
            startedAt = Timer.getFPGATimestamp();
    }

    public void start() {
        if (stopped) {
            startedAt = Timer.getFPGATimestamp() - get();
        } else {
            startedAt = Timer.getFPGATimestamp();
        }
        stopped = false;
    }

    public void stop() {
        stoppedAt = Timer.getFPGATimestamp();
        stopped = true;
    }
}
