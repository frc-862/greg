package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.LightningMath;
import frc.robot.subsystems.ShooterAngle;

public class SetIndexAngle extends CommandBase {
    private final ShooterAngle indexer;
    private final double angle;

    public SetIndexAngle(ShooterAngle indexer, double angle) {
        this.indexer = indexer;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        indexer.enableAutoAdjust();
        indexer.setAngle(angle);
    }

    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(angle, indexer.getAngle(), 1);
    }
}
