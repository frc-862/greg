package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class FullAutoFireMagazine extends CommandBase {
    Command cmd;
    private final Indexer indexer;
    private final Shooter shooter;
    private final Vision vision;

    public FullAutoFireMagazine(LightningDrivetrain dt, Vision v, Shooter s, ShooterAngle sa, Indexer i) {
        cmd = new FullAutoFireOne(dt, v, s, sa, i, true);
        // note not requiring indexer because we know FullAutoFireOne does
        this.indexer = i;
        this.shooter = s;
        this.vision = v;
    }

    @Override
    public void initialize() {
        cmd.initialize();
    }
    
    @Override
    public void execute() {
        cmd.execute();

        if (cmd.isFinished()) {
            if (!indexer.isEmpty()) {
                cmd.initialize();
            }
        }
    }

    @Override
    public boolean isFinished() {
        return indexer.isEmpty();
    }

    @Override
    public void end(boolean interrupted) {
        cmd.end(interrupted);
        indexer.safteyClosed();
        shooter.stop();
        vision.ringOff();
    }
}

