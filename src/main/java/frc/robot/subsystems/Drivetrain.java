/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

import java.util.function.Consumer;

public class Drivetrain extends SubsystemBase {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID  = 1;
    public static final int LEFT_2_CAN_ID  = 2;
    public static final int LEFT_3_CAN_ID  = 3;
    public static final int RIGHT_1_CAN_ID = 4;
    public static final int RIGHT_2_CAN_ID = 5;
    public static final int RIGHT_3_CAN_ID = 6;
    public static final int SHIFTER_FWD_CHANNEL     = 0;
    public static final int SHIFTER_REVERSE_CHANNEL = 7;

    private final String name = "DRIVETRAIN";

    private CANSparkMax leftMaster;
    private CANSparkMax leftSlave1;
    private CANSparkMax leftSlave2;

    private CANEncoder leftEncoder;
    private CANPIDController leftPIDFController;

    private CANSparkMax rightMaster;
    private CANSparkMax rightSlave1;
    private CANSparkMax rightSlave2;

    private CANEncoder rightEncoder;
    private CANPIDController rightPIDFController;

    public Drivetrain() {
        setName(name);

        leftMaster = new CANSparkMax(RobotMap.LEFT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave1 = new CANSparkMax(RobotMap.LEFT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave2 = new CANSparkMax(RobotMap.LEFT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftEncoder = new CANEncoder(leftMaster);

        leftPIDFController = leftMaster.getPIDController();

        rightMaster = new CANSparkMax(RobotMap.RIGHT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave1 = new CANSparkMax(RobotMap.RIGHT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave2 = new CANSparkMax(RobotMap.RIGHT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        rightEncoder = new CANEncoder(rightMaster);

        rightPIDFController = rightMaster.getPIDController();

        initMotorDirections();

        withEachMotor((m) -> m.setOpenLoopRampRate(Constants.OPEN_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setClosedLoopRampRate(Constants.CLOSE_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setIdleMode(IdleMode.kBrake));

        leftPIDFController.setP(Constants.leftGains.getkP());
        leftPIDFController.setI(Constants.leftGains.getkI());
        leftPIDFController.setD(Constants.leftGains.getkD());
        leftPIDFController.setFF(Constants.leftGains.getkFF());
        leftPIDFController.setIZone(Constants.leftGains.getkIz());
        leftPIDFController.setOutputRange(Constants.leftGains.getkMinOutput(), Constants.leftGains.getkMaxOutput());

        rightPIDFController.setP(Constants.rightGains.getkP());
        rightPIDFController.setI(Constants.rightGains.getkI());
        rightPIDFController.setD(Constants.rightGains.getkD());
        rightPIDFController.setFF(Constants.rightGains.getkFF());
        rightPIDFController.setIZone(Constants.rightGains.getkIz());
        rightPIDFController.setOutputRange(Constants.rightGains.getkMinOutput(), Constants.leftGains.getkMaxOutput());

        REVGains.putGainsToBeTunedOnDash((name + "_RIGHT"), Constants.rightGains);
        REVGains.putGainsToBeTunedOnDash((name + "_LEFT"), Constants.leftGains);

        if (Constants.DRIVETRAIN_DASHBOARD_ENABLED) {
            SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
            SmartDashboard.putNumber("Left Distance", getLeftDistance());
            SmartDashboard.putNumber("Right Velocity", getRightVelocity());
            SmartDashboard.putNumber("Right Distance", getRightDistance());
        }

        if (Constants.DRIVETRAIN_LOGGING_ENABLED) {
            DataLogger.addDataElement("leftVelocity", () -> leftEncoder.getVelocity());
            DataLogger.addDataElement("rightVelocity", () -> rightEncoder.getVelocity());
        }

    }

    public void init() {
        this.resetDistance();
    }

    @Override
    public void periodic() {
        REVGains.updateGainsFromDash((name + "_RIGHT"), Constants.rightGains, rightPIDFController);
        REVGains.updateGainsFromDash((name + "_LEFT"), Constants.leftGains, leftPIDFController);

        if (Constants.DRIVETRAIN_DASHBOARD_ENABLED) {
            SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
            SmartDashboard.putNumber("Left Distance", getLeftDistance());
            SmartDashboard.putNumber("Right Velocity", getRightVelocity());
            SmartDashboard.putNumber("Right Distance", getRightDistance());
        }
    }

    private void withEachMotor(Consumer<CANSparkMax> op) {
        op.accept(leftMaster);
        op.accept(leftSlave1);
        op.accept(leftSlave2);
        op.accept(rightMaster);
        op.accept(rightSlave1);
        op.accept(rightSlave2);
    }

    private void initMotorDirections() {
        rightMaster.setInverted(true);
        rightSlave1.follow(rightMaster, true);
        rightSlave2.follow(rightMaster, false);
        leftMaster.setInverted(false);
        leftSlave1.follow(leftMaster, true);
        leftSlave2.follow(leftMaster, false);
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

    public CANSparkMax getRightMaster() {
        return rightMaster;
    }

    public CANSparkMax getLeftMaster() {
        return leftMaster;
    }

    public DifferentialDrive getDifferentialDrive() {
        return null;
    }

    public void stop() {
    }
}
