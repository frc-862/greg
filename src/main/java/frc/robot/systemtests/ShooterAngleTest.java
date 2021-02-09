package frc.robot.systemtests;

import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.fault.FaultCode;
import frc.robot.subsystems.ShooterAngle;

public class ShooterAngleTest extends AbstractTimedSystemTest {

    private static final double testLength = 1.0;

    private ShooterAngle shooterAngle;

    public ShooterAngleTest(ShooterAngle shooterAngle) {
       super("Testing Lead Screw", testLength, FaultCode.Codes.GENERAL);
       this.shooterAngle = shooterAngle;
       addRequirements(shooterAngle);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        // shooterAngle.setShooterAngle(360);
    }

    @Override
    public boolean didPass() {
        return true;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || didPass();
    }

    @Override
    public void end(boolean interrupted) {
        shooterAngle.stop();
        shooterAngle.resetDistance();
    }

}
