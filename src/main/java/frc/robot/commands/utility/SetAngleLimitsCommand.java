package frc.robot.commands.utility;

import frc.lightning.util.StatefulCommand;
import frc.robot.subsystems.LeadScrew;


public class SetAngleLimitsCommand extends StatefulCommand {

    final private double power = 0.2;

    enum States { checkUpper, checkLower, done };

    private final LeadScrew leadScrew;

    public SetAngleLimitsCommand(LeadScrew leadScrew) {
        super(States.checkUpper);
        this.leadScrew = leadScrew;
        addRequirements(this.leadScrew);
    }

    @Override
    public void initialize() {
        setState(States.checkUpper);
        super.initialize();
    }

    private double previousAngle = 0;
    public void checkUpperEnter() {
        previousAngle = leadScrew.getAngle();
        leadScrew.setPower(power);
    }

    public void checkUpper() {
        if (leadScrew.atUpperLimit()) {
            setState(States.checkLower);
        }

        if (timeInState() > 0.5 && previousAngle == leadScrew.getAngle()) {
            setState(States.done);
        }
    }

    public void checkLowerEnter() {
        previousAngle = leadScrew.getAngle();
        leadScrew.setPower(-power);
    }

    public void checkLower() {
        if (leadScrew.atLowerLimit()) {
            // leadScrew.writeLimits(); // TODO add back?
            setState(States.done);
        }

        if (timeInState() > 0.5 && previousAngle == leadScrew.getAngle()) {
            setState(States.done);
        }
    }

    public void done() {
        leadScrew.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return getState() == States.done;
    }

    @Override
    public void end(boolean interrupted) {
        leadScrew.setPower(0);
    }
    
}
