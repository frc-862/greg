package frc.robot.systemtests;

import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.fault.FaultCode;
import frc.robot.subsystems.Shooter;

public class ShooterMotorTest extends AbstractTimedSystemTest {

    private static final double testLength = 1.0;

    private Shooter shooter;

    public ShooterMotorTest(Shooter shooter) {
       super("Testing Shooter Motors", testLength, FaultCode.Codes.GENERAL);
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
        return shooter.getFlywheelMotor1Velocity() > 400 
                    && shooter.getFlywheelMotor2Velocity() > 1900
                    && shooter.getFlywheelMotor3Velocity() > 400;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || didPass();
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
        shooter.resetDistance();
    }

}
