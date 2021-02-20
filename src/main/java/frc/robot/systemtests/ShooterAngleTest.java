package frc.robot.systemtests;

import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.fault.FaultCode;
import frc.robot.subsystems.LeadScrew;

public class ShooterAngleTest extends AbstractTimedSystemTest {
    private static final double testLength = 2.0;
    private LeadScrew leadScrew;
    private double initialAngle;

    public ShooterAngleTest(LeadScrew leadScrew) {
       super("Testing Lead Screw", testLength, FaultCode.Codes.GENERAL);
       this.leadScrew = leadScrew;
       addRequirements(leadScrew);
    }

    @Override
    public void initialize() {
        super.initialize();
        initialAngle = leadScrew.getAngle();
    }

    @Override
    public void execute() {
        super.execute();
        leadScrew.setAngle(30d);
        leadScrew.adjusterControlLoop();
    }

    @Override
    public boolean didPass() {
        return leadScrew.getAngle() == 30d;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() || didPass();
    }

    @Override
    public void end(boolean interrupted) {
        leadScrew.setAngle(initialAngle);
        leadScrew.adjusterControlLoop();
    }
}
