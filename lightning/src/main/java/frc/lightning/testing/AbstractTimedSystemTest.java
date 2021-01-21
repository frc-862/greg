package frc.lightning.testing;

import edu.wpi.first.wpilibj.Timer;
import frc.lightning.fault.FaultCode;

public abstract class AbstractTimedSystemTest extends SystemTest {

    private double timeAtStart;
    
    private final double timeout;

    public AbstractTimedSystemTest(String msg, double timeout, FaultCode.Codes code) {
        this(msg, timeout, code, Priority.DONT_CARE);
    }

    public AbstractTimedSystemTest(String msg, double timeout, FaultCode.Codes code, Priority priority) {
        super(msg, code, priority);
        this.timeout = timeout;
    }

    @Override
    public void initialize() {
        timeAtStart = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {
        return elapsed() > timeout;
    }

    protected double elapsed() {
        return Timer.getFPGATimestamp() - timeAtStart;
    }
}