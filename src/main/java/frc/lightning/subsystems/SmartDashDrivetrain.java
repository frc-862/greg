package frc.lightning.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SmartDashDrivetrain extends SubsystemBase {
    private final LightningDrivetrain drivetrain;

    public SmartDashDrivetrain(LightningDrivetrain dt) {
        this.drivetrain = dt;

        SmartDashboard.putNumber("Left Velocity", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("Left Distance", drivetrain.getLeftDistance());
        SmartDashboard.putNumber("Right Velocity", drivetrain.getRightVelocity());
        SmartDashboard.putNumber("Right Distance", drivetrain.getRightDistance());

        SmartDashboard.putNumber("Pose Rotation Deg", drivetrain.getPose().getRotation().getDegrees());
        SmartDashboard.putNumber("Pose Trans Y", drivetrain.getPose().getTranslation().getY());
        SmartDashboard.putNumber("Pose Trans X", drivetrain.getPose().getTranslation().getX());
        SmartDashboard.putNumber("Pose Trans Norm", drivetrain.getPose().getTranslation().getNorm());

        SmartDashboard.putNumber("Heading", drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Right Wheel Speed", drivetrain.getSpeeds().rightMetersPerSecond);
        SmartDashboard.putNumber("Left Wheel Speed", drivetrain.getSpeeds().leftMetersPerSecond);

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Velocity", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("Left Distance", drivetrain.getLeftDistance());
        SmartDashboard.putNumber("Right Velocity", drivetrain.getRightVelocity());
        SmartDashboard.putNumber("Right Distance", drivetrain.getRightDistance());
        
        SmartDashboard.putNumber("Pose Rotation Deg", drivetrain.getPose().getRotation().getDegrees());
        SmartDashboard.putNumber("Pose Trans Y", drivetrain.getPose().getTranslation().getY());
        SmartDashboard.putNumber("Pose Trans X", drivetrain.getPose().getTranslation().getX());
        SmartDashboard.putNumber("Pose Trans Norm", drivetrain.getPose().getTranslation().getNorm());

        SmartDashboard.putNumber("Heading", drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Right Wheel Speed", drivetrain.getSpeeds().rightMetersPerSecond);
        SmartDashboard.putNumber("Left Wheel Speed", drivetrain.getSpeeds().leftMetersPerSecond);
    }
}
