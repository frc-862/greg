package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.commands.shooter.SpinUpFlywheelVelocity;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class FullAutoFireOne extends SequentialCommandGroup {
    private final LightningDrivetrain drivetrain;
    private final Vision vision;
    private final Shooter shooter;
    private final ShooterAngle shooterAngle;
    private final Indexer indexer;
    private final boolean fullAuto;

    public FullAutoFireOne(LightningDrivetrain dt, Vision v, Shooter s, ShooterAngle sa, Indexer i, boolean fullAuto) {
        this.drivetrain = dt;
        this.vision = v;
        this.shooter = s;
        this.shooterAngle = sa;
        this.indexer = i;
        this.fullAuto = fullAuto;

        addCommands(
            new WaitForVision(vision),
            new ParallelCommandGroup(
                new VisionRotate(drivetrain,vision),
                new VisionShooterAngle(shooterAngle, vision),
                new SpinUpFlywheelVelocity(shooter, vision.getBestShooterVelocity())
            ),
            new FeedPowerCell(indexer, shooter, fullAuto)
        );
    }
}
