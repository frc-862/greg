package frc.robot.systemtests.drivetrain;

import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Indexer;
import frc.lightning.fault.FaultCode;

public class IndexerSystemTest extends AbstractTimedSystemTest {
    private static final double testLength = 2.0;
    private final Indexer indexer;

    public IndexerSystemTest(Indexer indexer) {
       super("Testing Indexer", testLength, FaultCode.Codes.GENERAL);
       this.indexer = indexer;
       addRequirements(indexer);
    }

    @Override
    public void execute() {
        super.execute();
        indexer.setPower(1d);
    }

    @Override
    public boolean didPass() {
        return indexer.getPower() == 1d;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || didPass();
    }

    @Override
    public void end(boolean interrupted) {
       indexer.stop();
    }
}
