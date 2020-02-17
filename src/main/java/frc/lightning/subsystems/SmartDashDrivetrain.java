package frc.lightning.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Map;

public class SmartDashDrivetrain extends SubsystemBase {
    public SmartDashDrivetrain(LightningDrivetrain drivetrain) {
        final var tab = Shuffleboard.getTab("Drivetrain");

        tab.addNumber("Left Velocity", () -> drivetrain.getLeftVelocity())
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", -13d, "max", 13d));
        tab.addNumber("Right Velocity", () -> drivetrain.getRightVelocity());

        tab.addNumber("Left Distance", () -> drivetrain.getLeftDistance());
        tab.addNumber("Right Distance", () -> drivetrain.getRightDistance());

        tab.addNumber("Pose Rotation Deg", () -> drivetrain.getPose().getRotation().getDegrees());
        tab.addNumber("Pose Trans Y", () -> drivetrain.getPose().getTranslation().getY());
        tab.addNumber("Pose Trans X", () -> drivetrain.getPose().getTranslation().getX());
        tab.addNumber("Pose Trans Norm", () -> drivetrain.getPose().getTranslation().getNorm());

        tab.addNumber("Heading", () -> drivetrain.getHeading().getDegrees());
        tab.addNumber("Right Wheel Speed", () -> drivetrain.getSpeeds().rightMetersPerSecond);
        tab.addNumber("Left Wheel Speed", () -> drivetrain.getSpeeds().leftMetersPerSecond);
    }
}
