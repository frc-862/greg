package frc.lightning.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class RumbleCommand extends CommandBase {
    final double duration = 0.5;

    Runnable action;
    XboxController operator;
    double start;

    public RumbleCommand(XboxController operator, Runnable cmd) {
        action = cmd;
        this.operator = operator;
    }

    @Override
    public void initialize() {
        start = Timer.getFPGATimestamp();
        operator.setRumble(GenericHID.RumbleType.kLeftRumble,1.0);
        operator.setRumble(GenericHID.RumbleType.kRightRumble,1.0);
        action.run();
    }

    @Override
    public void end(boolean interrupted) {
        operator.setRumble(GenericHID.RumbleType.kLeftRumble,0.0);
        operator.setRumble(GenericHID.RumbleType.kRightRumble,0.0);
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - start >= duration;
    }
}
