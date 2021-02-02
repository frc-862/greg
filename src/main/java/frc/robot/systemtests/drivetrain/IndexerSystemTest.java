package frc.robot.systemtests.drivetrain;

import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Indexer;
import frc.lightning.fault.FaultCode;

public class IndexerSystemTest extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final Indexer indexer;

    public IndexerSystemTest(Indexer indexer) {
       super("", testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
       this.indexer = indexer;
       addRequirements(indexer);
    }

    @Override
    public void execute() {
        super.execute();
        indexer.toShooter();
    }

    @Override
    public boolean didPass() {
        return indexer.getPower() > 0;
    }

    @Override
    public void end(boolean interrupted) {
       indexer.stop();
    }
}
