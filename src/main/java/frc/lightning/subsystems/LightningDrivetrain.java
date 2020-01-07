package frc.lightning.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface LightningDrivetrain {
    public abstract void setPower(double left, double right);
    public abstract void setVelocity(double left, double right);

    public abstract void brake();
    public abstract void coast();

    public abstract double getLeftDistance();
    public abstract double getRightDistance();
    public abstract double getLeftVelocity();
    public abstract double getRightVelocity();

    public abstract void resetDistance();

    public default void stop() {
        setPower(0,0);
        brake();
    }

    public default double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2;
    }

    public default double getVelocity() {
        return (getLeftVelocity() + getRightVelocity()) / 2;
    }
}