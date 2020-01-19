package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class FullAutoFireMagazine extends PerpetualCommand {
    private final Indexer indexer;

    public FullAutoFireMagazine(LightningDrivetrain dt, Vision v, Shooter s, ShooterAngle sa, Indexer i) {
        super(new FullAutoFireOne(dt, v, s, sa, i));
        // note not requiring indexer because we know FullAutoFireOne does
        this.indexer = i;
    }

    @Override
    public boolean isFinished() {
        return indexer.getPowerCellCount() == 0;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        indexer.safetyOn();
    }
}

