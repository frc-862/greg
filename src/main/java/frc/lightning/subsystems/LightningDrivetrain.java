package frc.lightning.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface LightningDrivetrain extends Subsystem {
    public default void init() {
    }

    public void setPower(double left, double right);

    public void setVelocity(double left, double right);

    public void resetDistance();

    public double getLeftDistance();

    public double getRightDistance();

    public double getLeftVelocity();

    public double getRightVelocity();

    public DifferentialDrive getDifferentialDrive();

    public default void stop() {
        setPower(0, 0);
    }

    public void brake();

    public void coast();

    public default double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public default double getVelocity() {
        return (getRightVelocity() + getLeftVelocity()) / 2;
    }
}

