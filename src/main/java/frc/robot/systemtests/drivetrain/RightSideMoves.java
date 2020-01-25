package frc.robot.systemtests.drivetrain;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.util.FaultCode;

public class RightSideMoves extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final LightningDrivetrain drivetrain;
    private double startPosition;

    public RightSideMoves(LightningDrivetrain drivetrain) {
       super(testLength, FaultCode.Codes.RIGHT_DRIVE_FAILURE);
       this.drivetrain = drivetrain;
       addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        super.initialize();
        drivetrain.initMotorDirections();
        startPosition = drivetrain.getRightDistance();
    }

    @Override
    public void execute() {
        super.execute();
        drivetrain.setPower(0.0, 0.5);
    }

    @Override
    public boolean didPass() {
        return startPosition < drivetrain.getRightDistance();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
