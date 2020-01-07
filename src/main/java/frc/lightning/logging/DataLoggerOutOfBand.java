package frc.lightning.logging;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Notifier;

public class DataLoggerOutOfBand implements DoubleSupplier {
    private double lastValue;

    public DataLoggerOutOfBand(DoubleSupplier fn) {
        this(fn, 0.02);
    }

    public DataLoggerOutOfBand(DoubleSupplier fn, double period) {
        DataLoggerOutOfBand me = this;

        var notifier = new Notifier(new Runnable() {
            @Override
            public void run() {
                double newValue = fn.getAsDouble();
                synchronized(me) {
                    lastValue = newValue;
                }
            }
        });

        notifier.startPeriodic(period);
    }

    @Override
    public double getAsDouble() {
        synchronized(this) {
            return lastValue;
        }
    }
}