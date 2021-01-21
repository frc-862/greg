package frc.lightning.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.logging.CommandLogger;

public abstract class LoggedCommand extends CommandBase {
    protected CommandLogger logger = new CommandLogger(getClass().getSimpleName());

    @Override
    public void initialize() {
        logger.reset();
    }

    @Override
    public void execute() {
        logger.write();
    }

    @Override
    public void end(boolean interrupted) {
        logger.drain();
        logger.flush();
    }
}
