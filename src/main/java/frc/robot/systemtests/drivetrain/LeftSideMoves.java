package frc.robot.systemtests.drivetrain;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.util.FaultCode;

public class LeftSideMoves extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final LightningDrivetrain drivetrain;
    private double startPosition;

    public LeftSideMoves(LightningDrivetrain drivetrain) {
        super(testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        super.initialize();
        startPosition = drivetrain.getLeftDistance();
        drivetrain.setPower(0.5, 0.0);
    }

    @Override
    public boolean didPass() {
        return startPosition < drivetrain.getLeftDistance();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
