package frc.robot.systemtests.drivetrain;

import frc.lightning.fault.FaultCode;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Collector;

public class CollectorSystemTest extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final Collector collector;
    private double startPosition;

    public CollectorSystemTest(Collector collector) {
       super("", testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
       this.collector = collector;
       addRequirements(collector);
    }

    @Override
    public void initialize() {
        super.initialize();
        collector.initMotorDirections();
        startPosition = collector.getLeftDistance();
    }

    @Override
    public void execute() {
        super.execute();
        collector.setPower(0.5, 0.0);
    }

    @Override
    public boolean didPass() {
        return startPosition < collector.getLeftDistance();
    }


    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }
}
