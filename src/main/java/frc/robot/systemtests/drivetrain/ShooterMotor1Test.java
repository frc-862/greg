package frc.robot.systemtests.drivetrain;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.fault.FaultCode;
import frc.robot.subsystems.Shooter;

public class ShooterMotor1Test extends AbstractTimedSystemTest {
    private static final double testLength = 1.0;
    private double startPosition;
    private Shooter shooter;
    private int tolerance = 5; 

    public ShooterMotor1Test(Shooter shooter) {
       super("", testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
       this.shooter = shooter;
       addRequirements(shooter);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        shooter.setShooterVelocity(2000);
    }

    @Override
    public boolean didPass() {
        return shooter.getFlywheelMotor1Velocity() >= 2000 - tolerance;
    }


    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        shooter.resetDistance();
    }
}
