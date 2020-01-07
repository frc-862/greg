package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.RobotConstants;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Drivetrain extends SubsystemBase implements LightningDrivetrain {
    final protected int motorCountPerSide = 1;
    DifferentialDrive differentialDrive = null;
    CANSparkMax left[] = new CANSparkMax[motorCountPerSide];
    CANSparkMax right[] = new CANSparkMax[motorCountPerSide];

    public Drivetrain() {
        for (var i = 0; i < motorCountPerSide; ++i) {
            left[i] = new CANSparkMax(RobotConstants.leftCANID + i, CANSparkMax.MotorType.kBrushless);
            right[i] = new CANSparkMax(RobotConstants.rightCANID + i, CANSparkMax.MotorType.kBrushless);
        }

        forEachMotor((var motor, var master) -> {
            motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
            motor.setOpenLoopRampRate(RobotConstants.openLoopRampRate);
            motor.setClosedLoopRampRate(RobotConstants.closedLoopRampRate);
            motor.setSmartCurrentLimit(RobotConstants.currentLimit);
            motor.setSecondaryCurrentLimit(RobotConstants.maxCurrent);

            if (motor != master) {
                motor.follow(master);
            }

            motor.burnFlash(); // otherwise brownouts will loose our configuration
        });
    }

    public DifferentialDrive getDifferentialDrive() {
        if (differentialDrive == null) {
            differentialDrive = new DifferentialDrive(left[0], right[0]);
        }
        return differentialDrive;
    }

    public void forEachMotor(BiConsumer<CANSparkMax, CANSparkMax> action) {
        for (var i = 0; i < motorCountPerSide; ++i) {
            action.accept(left[i], left[0]);
            action.accept(right[i], right[0]);
        }
    }

    public void forEachMotor(Consumer<CANSparkMax> action) {
        for (var i = 0; i < motorCountPerSide; ++i) {
            action.accept(left[i]);
            action.accept(right[i]);
        }
    }

    public void forEachLeftMotor(BiConsumer<CANSparkMax,Integer> action) {
        for (var i = 0; i < motorCountPerSide; ++i) {
            action.accept(left[i], i);
        }
    }

    public void forEachRightMotor(BiConsumer<CANSparkMax,Integer> action) {
        for (var i = 0; i < motorCountPerSide; ++i) {
            action.accept(right[i], i);
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void setPower(double left, double right) {
       this.left[0].set(left);
       this.right[0].set(right);
    }

    @Override
    public void setVelocity(double left, double right) {
        this.left[0].set(left);
        this.right[0].set(right);
    }

    @Override
    public void brake() {
        forEachMotor(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kBrake));
    }

    @Override
    public void coast() {
        forEachMotor(motor -> motor.setIdleMode(CANSparkMax.IdleMode.kCoast));
    }

    @Override
    public double getLeftDistance() {
        return 0;
    }

    @Override
    public double getRightDistance() {
        return 0;
    }

    @Override
    public double getLeftVelocity() {
        return 0;
    }

    @Override
    public double getRightVelocity() {
        return 0;
    }

    @Override
    public void resetDistance() {
        left[0].getAlternateEncoder();
    }
}
