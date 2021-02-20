package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.commands.shooter.SpinUpFlywheelVelocity;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.LeadScrew;
import frc.robot.subsystems.Vision;

public class FullAutoFireOne extends SequentialCommandGroup {
    private final LightningDrivetrain drivetrain;
    private final Vision vision;
    private final Shooter shooter;
    private final LeadScrew leadScrew;
    private final Indexer indexer;
    private final boolean fullAuto;

    public FullAutoFireOne(LightningDrivetrain dt, Vision v, Shooter s, LeadScrew sa, Indexer i, boolean fullAuto) {
        this.drivetrain = dt;
        this.vision = v;
        this.shooter = s;
        this.leadScrew = sa;
        this.indexer = i;
        this.fullAuto = fullAuto;

        addCommands(
            new InstantCommand(() -> vision.bothRingsOn()),
            new InstantCommand(() -> indexer.safteyOpen()),
            new WaitForVision(vision),
            new ParallelCommandGroup(
                new VisionRotate(drivetrain, vision),
                new VisionLeadScrew(leadScrew, vision),
                new SpinUpFlywheelVelocity(shooter, vision)
            ),
            new FeedPowerCell(indexer, fullAuto)
        );
    }
}
