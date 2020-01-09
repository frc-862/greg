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
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Velocity", drivetrain.getLeftVelocity());
        SmartDashboard.putNumber("Left Distance", drivetrain.getLeftDistance());
        SmartDashboard.putNumber("Right Velocity", drivetrain.getRightVelocity());
        SmartDashboard.putNumber("Right Distance", drivetrain.getRightDistance());
    }
}
