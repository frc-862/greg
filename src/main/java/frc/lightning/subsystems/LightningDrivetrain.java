package frc.lightning.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.lightning.util.RamseteGains;

import java.util.Objects;

public interface LightningDrivetrain extends Subsystem {
    public class DriveCommand {
        public double leftCommand;
        public double rightCommand;

        public DriveCommand(double left, double right) {
            leftCommand = left;
            rightCommand = right;
        }

        @Override
        public String toString() {
            return "DriveCommand{" +
                   "leftCommand=" + leftCommand +
                   ", rightCommand=" + rightCommand +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DriveCommand that = (DriveCommand) o;
            return Double.compare(that.leftCommand, leftCommand) == 0 &&
                   Double.compare(that.rightCommand, rightCommand) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(leftCommand, rightCommand);
        }
    }

    public default void init() {
    }

    public void setPower(double left, double right);


    public default void setPower(DriveCommand cmd) {
        setPower(cmd.leftCommand, cmd.rightCommand);
    }

    public void setVelocity(double left, double right);

    public default void setVelocity(DriveCommand cmd) {
        setVelocity(cmd.leftCommand, cmd.rightCommand);
    }

    public void resetDistance();

    public double getLeftDistance();

    public double getRightDistance();

    public double getLeftVelocity();

    public double getRightVelocity();

    public default void stop() {
        setPower(0, 0);
    }

    public void setOutput(double leftVolts, double rightVolts);

    public void brake();

    public void coast();

    public void initMotorDirections();

    public void resetSensorVals();

    public RamseteGains getConstants();

    public default Rotation2d getHeading() { return new Rotation2d(0d); }

    public default SimpleMotorFeedforward getFeedforward() {
        return null;
    }

    public DifferentialDriveKinematics getKinematics();

    public Pose2d getPose();

    public Pose2d getRelativePose();

    public void setRelativePose();

    public DifferentialDriveWheelSpeeds getSpeeds();

    public PIDController getLeftPidController();
    
    public PIDController getRightPidController();

    public double getRightVolts();
    
    public double getLeftVolts();

    public void setRamseteOutput(double leftVolts, double rightVolts);

    public default double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public default double getVelocity() {
        return (getRightVelocity() + getLeftVelocity()) / 2;
    }
}

