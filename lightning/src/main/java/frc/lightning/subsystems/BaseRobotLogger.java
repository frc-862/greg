package frc.lightning.subsystems;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;

public class BaseRobotLogger extends SubsystemBase {
    private final LightningDrivetrain drivetrain;
    private final IMU imu;

    public BaseRobotLogger(LightningDrivetrain dt, IMU imu) {
        this.drivetrain = dt;
        this.imu = imu;

        DataLogger.addDataElement("LeftVelocity", () -> drivetrain.getLeftVelocity());
        DataLogger.addDataElement("RightVelocity", () -> drivetrain.getRightVelocity());
        DataLogger.addDataElement("PoseRotationDeg", () -> drivetrain.getPose().getRotation().getDegrees());
        DataLogger.addDataElement("PoseTransY", () -> drivetrain.getPose().getTranslation().getY());
        DataLogger.addDataElement("PoseTransX", () -> drivetrain.getPose().getTranslation().getX());
        DataLogger.addDataElement("PoseTransNorm", () -> drivetrain.getPose().getTranslation().getNorm());
        //DataLogger.addDataElement("Heading", () -> drivetrain.getHeading().getDegrees());
        DataLogger.addDataElement("RightWheelSpeed", () -> drivetrain.getSpeeds().rightMetersPerSecond);
        DataLogger.addDataElement("LeftWheelSpeed", () -> drivetrain.getSpeeds().leftMetersPerSecond);
        DataLogger.addDataElement("LeftDistanceMeters", () -> drivetrain.getLeftDistance());
        DataLogger.addDataElement("RightDistanceMeters", () -> drivetrain.getRightDistance());
        DataLogger.addDataElement("Voltage", () -> RobotController.getBatteryVoltage());
        DataLogger.addDataElement("Heading", () -> this.imu.getHeading().getDegrees());
    }
}
