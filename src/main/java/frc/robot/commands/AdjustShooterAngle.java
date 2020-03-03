package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterAngle;

public class AdjustShooterAngle extends CommandBase {
    private final ShooterAngle shooterAngle;
    private final double desiredAngle;

    public AdjustShooterAngle(ShooterAngle sa, double angle) {
        this.shooterAngle = sa;
        desiredAngle = angle;
    }

    @Override
    public void initialize() {
        shooterAngle.setAngle(desiredAngle);
    }

    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(desiredAngle, shooterAngle.getAngle(), Constants.shooterAngleTolerance);
    }
}
