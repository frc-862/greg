/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

import java.util.function.Consumer;

public class TwikiDrivetrain extends SubsystemBase implements LightningDrivetrain {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID = 1;
    public static final int RIGHT_1_CAN_ID = 4;

    private final String name = "DRIVETRAIN2MOTOR";

    private CANSparkMax leftMaster;

    private CANEncoder leftEncoder;
    private CANPIDController leftPIDFController;

    private CANSparkMax rightMaster;

    private CANEncoder rightEncoder;
    private CANPIDController rightPIDFController;

    public TwikiDrivetrain() {
        setName(name);

        leftMaster = new CANSparkMax(RobotMap.LEFT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftEncoder = new CANEncoder(leftMaster);
        leftPIDFController = leftMaster.getPIDController();

        rightMaster = new CANSparkMax(RobotMap.RIGHT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
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
        op.accept(rightMaster);
    }

    @Override
    public void initMotorDirections() {
        rightMaster.setInverted(true);
        leftMaster.setInverted(false);
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

    private DifferentialDrive differentialDrive = null;

    public DifferentialDrive getDifferentialDrive() {
        if (differentialDrive == null) {
            differentialDrive = new DifferentialDrive(getLeftMaster(), getRightMaster());
            differentialDrive.setRightSideInverted(false);
        }
        return differentialDrive;
    }

    @Override
    public void brake() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kBrake));
    }

    @Override
    public void coast() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kCoast));
    }

    public CANSparkMax[] getLeftMotors() {
        CANSparkMax[] motors = new CANSparkMax[1];
        motors[0] = getLeftMaster();
        return motors;
    }

    public CANSparkMax[] getRightMotors() {
        CANSparkMax[] motors = new CANSparkMax[1];
        motors[0] = getRightMaster();
        return motors;
    }

    @Override
    public void setOutput(double leftVolts, double rightVolts) {
        // TODO Auto-generated method stub

    }

    @Override
    public DifferentialDriveKinematics getKinematics() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pose2d getPose() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DifferentialDriveWheelSpeeds getSpeeds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PIDController getLeftPidController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PIDController getRightPidController() {
        // TODO Auto-generated method stub
        return null;
    }
}
