package frc.lightning.testing;

import edu.wpi.first.wpilibj.Timer;
import frc.lightning.util.FaultCode;

public abstract class AbstractTimedSystemTest extends SystemTest {
    private double timeAtStart;
    private final double timeout;

    public AbstractTimedSystemTest(double timeout, FaultCode.Codes code) {
        super(code, Priority.DONT_CARE);
        this.timeout = timeout;
    }

    public AbstractTimedSystemTest(double timeout, FaultCode.Codes code, Priority priority) {
        super(code, priority);
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