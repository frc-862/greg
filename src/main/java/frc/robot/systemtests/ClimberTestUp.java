package frc.robot.systemtests;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.robot.subsystems.Climber;
import frc.lightning.fault.FaultCode;

public class ClimberTestUp extends AbstractTimedSystemTest {
    private final Climber climber;
    private static final double testLength = 5;

    public ClimberTestUp(Climber climber) {
       super("", testLength, FaultCode.Codes.LEFT_DRIVE_FAILURE);
       this.climber = climber;
       addRequirements(climber);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        climber.up();
    }

    @Override
    public boolean didPass() {
        return climber.getLeft() > 0 && climber.getRight() > 0;
    }


    @Override
    public void end(boolean interrupted) {
        climber.stop();
    }
}
