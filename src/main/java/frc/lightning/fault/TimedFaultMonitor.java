package frc.lightning.fault;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import frc.lightning.fault.FaultCode.Codes;

/**
 * A fault monitor that checks if a condition has existed for a given period of time
 */
public class TimedFaultMonitor extends AbstractFaultMonitor {
    final double duration;
    final BooleanSupplier fn;
    final Timer timer = new Timer();

    public TimedFaultMonitor(Codes code, BooleanSupplier fn, double duration, String msg) {
        super(code, msg);
        this.fn = fn;
        this.duration = duration;
    }

    @Override
    public boolean checkFault() {
        if (!fn.getAsBoolean()) {
            timer.reset();
        }
        return timer.get() >= duration;
    }
}
