package frc.robot.systemtests.drivetrain;

import frc.lightning.fault.FaultCode;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Collector;

public class CollectorExtendTest extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final Collector collector;

    public CollectorExtendTest(Collector collector) {
       super("", testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
       this.collector = collector;
       addRequirements(collector);
    }

    @Override
    public void initialize() {
        super.initialize();
        collector.puterOuterIn();
    }

    @Override
    public void execute() {
        super.execute();
        collector.toggleCollector();
    }

    @Override
    public boolean didPass() {
        return  collector.isOut();
    }


    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }
}
