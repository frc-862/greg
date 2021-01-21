package frc.lightning.fault;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj.Timer;
import frc.lightning.fault.FaultCode.Codes;

/**
 * A fault monitor that ensures a given value remains unchanged for a duration of time
 */
public class UnchangingFaultMonitor extends AbstractFaultMonitor {
    final double duration;
    final DoubleSupplier fn;
    final Timer timer = new Timer();
    private final double epsilon;
    private double previousValue;

    public UnchangingFaultMonitor(Codes code, DoubleSupplier fn, double duration, double epsilon, String msg) {
        super(code, msg);
        this.fn = fn;
        this.epsilon = epsilon;
        this.duration = duration;
        previousValue = fn.getAsDouble();
        timer.start();
    }

    @Override
    public boolean checkFault() {
        double newValue = fn.getAsDouble();
        if (Math.abs(newValue - previousValue) > epsilon) {
            timer.reset();
            previousValue = newValue;
            return false;
        }
        return timer.get() >= duration;
    }
}