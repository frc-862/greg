package frc.robot.systemtests.drivetrain;

import frc.lightning.fault.FaultCode;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Collector;

public class CollectorSpinUpTest extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final Collector collector;
    private double startPosition1;
    private double startPosition2;

    public CollectorSpinUpTest(Collector collector) {
       super("", testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
       this.collector = collector;
       addRequirements(collector);
    }

    @Override
    public void initialize() {
        super.initialize();
        startPosition1 = collector.getLinearVelocity();
        startPosition2 = collector.getLongitudinalVelocity();
    }

    @Override
    public void execute() {
        super.execute();
        collector.setPower(1);
    }

    @Override
    public boolean didPass() {
        return  startPosition1 < collector.getLinearVelocity() && startPosition2 < collector.getLongitudinalVelocity();
    }


    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }
}
