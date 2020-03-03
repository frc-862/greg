package frc.robot.commands.utility;

import frc.lightning.util.StatefulCommand;
import frc.robot.subsystems.ShooterAngle;


public class SetAngleLimitsCommand extends StatefulCommand {
    final private double power = 0.2;

    enum States { checkUpper, checkLower, done };

    private final ShooterAngle shooterAngle;

    public SetAngleLimitsCommand(ShooterAngle shooterAngle) {
        super(States.checkUpper);
        this.shooterAngle = shooterAngle;
        addRequirements(this.shooterAngle);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        setState(States.checkUpper);
        super.initialize();
    }

    private double previousAngle = 0;
    public void checkUpperEnter() {
        previousAngle = shooterAngle.getAngle();
        shooterAngle.setPower(power);
    }

    public void checkUpper() {
        if (shooterAngle.atUpperLimit()) {
            setState(States.checkLower);
        }

        if (timeInState() > 0.5 && previousAngle == shooterAngle.getAngle()) {
            setState(States.done);
        }
    }

    public void checkLowerEnter() {
        previousAngle = shooterAngle.getAngle();
        shooterAngle.setPower(-power);
    }

    public void checkLower() {
        if (shooterAngle.atLowerLimit()) {
            shooterAngle.writeLimits();
            setState(States.done);
        }

        if (timeInState() > 0.5 && previousAngle == shooterAngle.getAngle()) {
            setState(States.done);
        }
    }

    public void done() {
        shooterAngle.setPower(0);
    }

    @Override
    public boolean isFinished() {
        return getState() == States.done;
    }

    @Override
    public void end(boolean interrupted) {
        shooterAngle.setPower(0);
    }
}
