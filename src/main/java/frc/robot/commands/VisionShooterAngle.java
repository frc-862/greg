package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class VisionShooterAngle extends CommandBase {
    private final ShooterAngle shooterAngle;
    private final Vision vision;

    public VisionShooterAngle( ShooterAngle sa, Vision vision) {

        this.shooterAngle = sa;

        // Intentionally not adding vision as a requirement
        // our use is "read only"
        this.vision = vision;

        addRequirements(shooterAngle);
    }

    @Override
    public void initialize() {
        shooterAngle.setDesiredAngle(vision.getBestShooterAngle());
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(shooterAngle.getAngle(),
                                          vision.getBestShooterAngle(),
                                          Constants.shooterAngleTolerance);
    }

}
