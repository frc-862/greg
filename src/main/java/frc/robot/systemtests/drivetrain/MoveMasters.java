package frc.robot.systemtests.drivetrain;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.util.FaultCode;

public class MoveMasters extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private final NeoDrivetrain drivetrain;
    private double leftStartPosition;
    private double rightStartPosition;

    public MoveMasters(NeoDrivetrain drivetrain) {
       super(testLength, FaultCode.Codes.INDIVIDUAL_DRIVE_MOTOR_ERROR);
       this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        super.initialize();
        drivetrain.resetDistance();
        leftStartPosition = drivetrain.getLeftDistance();
        rightStartPosition = drivetrain.getRightDistance();
        drivetrain.getLeftMotors()[0].set(0.25);
        drivetrain.getRightMotors()[0].set(0.25);
    }

    @Override
    public boolean didPass() {
        return ((leftStartPosition < drivetrain.getLeftDistance()) && (rightStartPosition < drivetrain.getRightDistance()));
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
