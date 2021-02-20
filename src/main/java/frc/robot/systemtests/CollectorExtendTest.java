package frc.robot.systemtests;

import frc.lightning.fault.FaultCode;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Collector;

public class CollectorExtendTest extends AbstractTimedSystemTest {
    private static final double testLength = 5.0;
    private final Collector collector;

    public CollectorExtendTest(Collector collector) {
       super("Testing Collector Extension", testLength, FaultCode.Codes.GENERAL);
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
        collector.retract();
        collector.extend();
    }

    @Override
    public boolean didPass() {
        return collector.isOut();
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
