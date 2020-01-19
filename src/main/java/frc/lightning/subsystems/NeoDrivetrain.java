/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.misc.REVGains;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NeoDrivetrain extends SubsystemBase implements LightningDrivetrain {
    private static final double CLOSE_LOOP_RAMP_RATE = 0.5;
    private static final double OPEN_LOOP_RAMP_RATE = 0.5;

    // DRIVETRAIN
    public final int firstLeftCanId;
    public final int firstRightCanId;

    private final String name = "DRIVETRAIN";
    private final int motorCount;

    private CANSparkMax[] leftMotors;
    private CANSparkMax leftMaster;
    private CANEncoder leftEncoder;
    private CANPIDController leftPIDFController;

    private CANSparkMax[] rightMotors;
    private CANSparkMax rightMaster;
    private CANEncoder rightEncoder;
    private CANPIDController rightPIDFController;

    public NeoDrivetrain(int motorCountPerSide, int firstLeftCanId, int firstRightCanId) {
        setName(name);
        this.motorCount = motorCountPerSide;
        this.firstLeftCanId = firstLeftCanId;
        this.firstRightCanId = firstRightCanId;

        leftMotors = new CANSparkMax[motorCount];
        rightMotors = new CANSparkMax[motorCount];
        for (int i = 0; i < motorCount; ++i) {
            leftMotors[i] = new CANSparkMax(i + firstLeftCanId, CANSparkMaxLowLevel.MotorType.kBrushless);
            rightMotors[i] = new CANSparkMax(i + firstRightCanId, CANSparkMaxLowLevel.MotorType.kBrushless);
        }

        leftMaster = leftMotors[0];
        leftEncoder = new CANEncoder(leftMaster);
        leftPIDFController = leftMaster.getPIDController();
        leftPIDFController.setFeedbackDevice(leftEncoder);

        rightMaster = rightMotors[0];
        rightEncoder = new CANEncoder(rightMaster);
        rightPIDFController = rightMaster.getPIDController();
        rightPIDFController.setFeedbackDevice(rightEncoder);

        withEachMotor((m) -> m.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setClosedLoopRampRate(CLOSE_LOOP_RAMP_RATE));
        brake();
    }

    private static void setGains(CANPIDController controller, REVGains gains) {
        controller.setP(gains.getkP());
        controller.setI(gains.getkI());
        controller.setD(gains.getkD());
        controller.setFF(gains.getkFF());
        controller.setIZone(gains.getkIz());
        controller.setOutputRange(gains.getkMinOutput(), gains.getkMaxOutput());
    }

    public void invertMotor() {

    }

    public void setLeftGains(REVGains gains) {
        setGains(leftPIDFController, gains);
    }

    public void setRightGains(REVGains gains) {
        setGains(rightPIDFController, gains);
    }

    public void setGains(REVGains gains) {
        setGains(leftPIDFController, gains);
        setGains(rightPIDFController, gains);
    }

    public void init() {
        this.resetDistance();
    }

    protected CANSparkMax getLeftMaster() {
        return leftMaster;
    }
    protected CANSparkMax getRightMaster() {
        return rightMaster;
    }

    protected void withEachMotor(Consumer<CANSparkMax> op) {
        for (var i = 0; i < motorCount; ++i) {
            op.accept(leftMotors[i]);
            op.accept(rightMotors[i]);
        }
    }

    protected void withEachMotorIndexed(BiConsumer<CANSparkMax, Integer> op) {
        for (var i = 0; i < motorCount; ++i) {
            op.accept(leftMotors[i], i);
            op.accept(rightMotors[i], i);
        }
    }

    protected void withEachSlaveMotor(BiConsumer<CANSparkMax, CANSparkMax> op) {
        for (var i = 1; i < motorCount; ++i) {
            op.accept(leftMotors[i], leftMaster);
            op.accept(rightMotors[i], rightMaster);
        }
    }

    protected void withEachSlaveMotorIndexed(BiConsumer<CANSparkMax, Integer> op) {
        for (var i = 1; i < motorCount; ++i) {
            op.accept(leftMotors[i], i);
            op.accept(rightMotors[i], i);
        }
    }

    protected void withEachLeftSlaveMotorIndexed(BiConsumer<CANSparkMax, Integer> op) {
        for (var i = 1; i < motorCount; ++i) {
            op.accept(leftMotors[i], i);
        }
    }

    protected void withEachRightSlaveMotorIndexed(BiConsumer<CANSparkMax, Integer> op) {
        for (var i = 1; i < motorCount; ++i) {
            op.accept(rightMotors[i], i);
        }
    }

    protected void withEachMotor(BiConsumer<CANSparkMax,CANSparkMax> op) {
        for (var i = 0; i < motorCount; ++i) {
            op.accept(leftMotors[i], leftMotors[0]);
            op.accept(rightMotors[i], rightMotors[0]);
        }
    }

    public void setPower(double left, double right) {
        rightMaster.set(right);
        leftMaster.set(left);
    }

    public void setVelocity(double left, double right) {
        this.rightPIDFController.setReference(right, ControlType.kVelocity);
        this.leftPIDFController.setReference(left, ControlType.kVelocity);
    }

    public void resetDistance() {
        leftEncoder.setPosition(0.0);
        rightEncoder.setPosition(0.0);
    }

    public double getLeftDistance() {
        return leftEncoder.getPosition();
    }

    public double getRightDistance() {
        return rightEncoder.getPosition();
    }

    public double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    @Override
    public void brake() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kBrake));
    }

    @Override
    public void coast() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kCoast));
    }

    protected CANSparkMax[] getRightMotors() {
        return rightMotors;
    }

    protected CANSparkMax[] getLeftMotors() {
        return leftMotors;
    }

    protected CANPIDController getLeftPIDFC() {
        return leftPIDFController;
    }

    protected CANPIDController getRightPIDFC() {
        return rightPIDFController;
    }
}
