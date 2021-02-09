package frc.robot.systemtests;

import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.fault.FaultCode;
import frc.robot.subsystems.ShooterAngle;

public class ShooterAngleTest extends AbstractTimedSystemTest {
    private static final double testLength = 2.0;
    private ShooterAngle shooterAngle;
    private double initialAngle;

    public ShooterAngleTest(ShooterAngle shooterAngle) {
       super("Testing Lead Screw", testLength, FaultCode.Codes.GENERAL);
       this.shooterAngle = shooterAngle;
       addRequirements(shooterAngle);
    }

    @Override
    public void initialize() {
        super.initialize();
        initialAngle = shooterAngle.getAngle();
    }

    @Override
    public void execute() {
        super.execute();
        shooterAngle.setAngle(30d);
        shooterAngle.adjusterControlLoop();
    }

    @Override
    public boolean didPass() {
        return shooterAngle.getAngle() == 30d;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || didPass();
    }

    @Override
    public void end(boolean interrupted) {
        shooterAngle.setAngle(initialAngle);
        shooterAngle.adjusterControlLoop();
    }
}
