package frc.robot.systemtests;

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
    }

    @Override
    public void execute() {
        super.execute();
        collector.collect();
    }

    @Override
    public boolean didPass() {
        return collector.getPowerLinear() >= 1 && collector.getPowerLongitudinal() >= 1;
    }


    @Override
    public boolean isFinished() {
        return super.isFinished() || didPass();
    }

    @Override
    public void end(boolean interrupted) {
        collector.stop();
    }
}
