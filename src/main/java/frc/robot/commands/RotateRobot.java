package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.IMU;

public class RotateRobot extends CommandBase {
    private final double desiredAngle;
    private final IMU imu;
    private final LightningDrivetrain drivetrain;

    public RotateRobot(LightningDrivetrain drivetrain, IMU imu, double desiredAngle) {
        this.drivetrain = drivetrain;
        this.imu = imu;
        this.desiredAngle = desiredAngle;
    }

    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(desiredAngle, imu.getYaw(), Constants.ROTATION_TOLERANCE);
    }
}
