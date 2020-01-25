/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.LightningMath;
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

    private AHRS navx;

    private DifferentialDriveKinematics kinematics;

    private DifferentialDriveOdometry odometry;

    private SimpleMotorFeedforward feedforward;

    private PIDController leftPIDController;
    
    private PIDController rightPIDController;

    private Pose2d pose = new Pose2d(0d, 0d, new Rotation2d());

    public NeoDrivetrain(int motorCountPerSide, int firstLeftCanId, int firstRightCanId, double trackWidth) {
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
        leftEncoder = leftMaster.getEncoder(EncoderType.kHallSensor, 42);//new CANEncoder(leftMaster);
        leftPIDFController = leftMaster.getPIDController();
        leftPIDFController.setFeedbackDevice(leftEncoder);

        rightMaster = rightMotors[0];
        rightEncoder = rightMaster.getEncoder(EncoderType.kHallSensor, 42);
        rightPIDFController = rightMaster.getPIDController();
        rightPIDFController.setFeedbackDevice(rightEncoder);

        withEachMotor((m) -> m.setOpenLoopRampRate(OPEN_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setClosedLoopRampRate(CLOSE_LOOP_RAMP_RATE));
        brake();

        navx = new AHRS(Port.kMXP);

        kinematics = new DifferentialDriveKinematics(trackWidth);

        odometry = new DifferentialDriveOdometry(getHeading(), pose);

        feedforward = new SimpleMotorFeedforward(Constants.kS, Constants.kV, Constants.kA);

        leftPIDController = new PIDController(Constants.left_kP, Constants.left_kI, Constants.left_kD);
        
        rightPIDController = new PIDController(Constants.right_kP, Constants.right_kI, Constants.right_kD);
        
        SmartDashboard.putNumber("RequestedLeftVolts", 0d);
        SmartDashboard.putNumber("RequestedRightVolts", 0d);

        SmartDashboard.putNumber("PoseRotationDeg", 0d);
        SmartDashboard.putNumber("PoseTransY", 0d);
        SmartDashboard.putNumber("PoseTransX", 0d);
        SmartDashboard.putNumber("PoseTransNorm", 0d);

        SmartDashboard.putNumber("TrackWidthMeters", getKinematics().trackWidthMeters);

        SmartDashboard.putNumber("RightTicksPerRev", rightEncoder.getCountsPerRevolution());
        SmartDashboard.putNumber("LeftTicksPerRev", leftEncoder.getCountsPerRevolution());

        SmartDashboard.putNumber("RightTicConversionFactor", rightEncoder.getPositionConversionFactor());
        SmartDashboard.putNumber("LeftTicConversionFactor", leftEncoder.getPositionConversionFactor());

        resetDistance();

        resetNavX();

        odometry.resetPosition(new Pose2d(), new Rotation2d());
        pose = odometry.update(getHeading(), getLeftDistance(), getRightDistance());

    }

    @Override
    public void periodic() {
        super.periodic();
        pose = odometry.update(getHeading(), getLeftDistance(), getRightDistance());

        SmartDashboard.putNumber("PoseRotationDeg", pose.getRotation().getDegrees());
        SmartDashboard.putNumber("PoseTransY", pose.getTranslation().getY());
        SmartDashboard.putNumber("PoseTransX", pose.getTranslation().getX());
        SmartDashboard.putNumber("PoseTransNorm", pose.getTranslation().getNorm());

        SmartDashboard.putNumber("Heading", navx.getAngle());
        SmartDashboard.putNumber("RightWheelSpeed", getSpeeds().rightMetersPerSecond);
        SmartDashboard.putNumber("LeftWheelSpeed", getSpeeds().leftMetersPerSecond);

        SmartDashboard.putNumber("LeftTicVal", leftEncoder.getPosition());
        SmartDashboard.putNumber("RightTicVal", rightEncoder.getPosition());

    }

    private static void setGains(CANPIDController controller, REVGains gains) {
        controller.setP(gains.getkP());
        controller.setI(gains.getkI());
        controller.setD(gains.getkD());
        controller.setFF(gains.getkFF());
        controller.setIZone(gains.getkIz());
        controller.setOutputRange(gains.getkMinOutput(), gains.getkMaxOutput());
    }

    @Override
    public DifferentialDriveKinematics getKinematics() { return kinematics; }

    @Override
    public Pose2d getPose() { return pose; }

    @Override
    public PIDController getLeftPidController() { return leftPIDController; }
    
    @Override
    public PIDController getRightPidController() { return rightPIDController; }

    @Override
    public DifferentialDriveWheelSpeeds getSpeeds() { return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity()); }

    @Override
    public Rotation2d getHeading() { return Rotation2d.fromDegrees(-navx.getAngle()); }

    @Override
    public void setOutput(double leftVolts, double rightVolts) {

        SmartDashboard.putNumber("RequestedLeftVolts", leftVolts);
        SmartDashboard.putNumber("RequestedRightVolts", rightVolts);

        leftMaster.setVoltage(leftVolts);
        rightMaster.setVoltage(rightVolts);
    }

    @Override
    public void setRamseteOutput(double leftVolts, double rightVolts) {
        setOutput(leftVolts, rightVolts); //-rightVolts);
    }

    @Override
    public void resetSensorVals() {
        LightningDrivetrain.super.resetSensorVals();
        resetNavX();
    }

    private void resetNavX() {
        navx.reset();
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

    protected CANSparkMax getLeftMaster() { return leftMaster; }
    protected CANSparkMax getRightMaster() { return rightMaster; }

    public void freeSlaves() {
        for (int i = 0; i < motorCount; ++i) {
            leftMotors[i] = new CANSparkMax(i + firstLeftCanId, CANSparkMaxLowLevel.MotorType.kBrushless);
            rightMotors[i] = new CANSparkMax(i + firstRightCanId, CANSparkMaxLowLevel.MotorType.kBrushless);
        }
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
        leftEncoder.setPosition(0d);
        rightEncoder.setPosition(0d);
    }

    public double getLeftDistance() {
        return LightningMath.rotationsToMetersTraveled(leftEncoder.getPosition());
    }

    public double getRightDistance() {
        return LightningMath.rotationsToMetersTraveled(rightEncoder.getPosition());
    }

    public double getLeftVelocity() {
        return LightningMath.rpmToMetersPerSecond(leftEncoder.getVelocity());
    }

    public double getRightVelocity() {
        return LightningMath.rpmToMetersPerSecond(rightEncoder.getVelocity());
    }

    @Override
    public void brake() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kBrake));
    }

    @Override
    public void coast() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kCoast));
    }

    public CANSparkMax[] getRightMotors() {
        return rightMotors;
    }

    public CANSparkMax[] getLeftMotors() {
        return leftMotors;
    }

    protected CANPIDController getLeftPIDFC() {
        return leftPIDFController;
    }

    protected CANPIDController getRightPIDFC() {
        return rightPIDFController;
    }

    public SimpleMotorFeedforward getFeedforward() {
        return feedforward;
    }

    public PIDController getLeftPIDController() {
        return leftPIDController;
    }

    public PIDController getRightPIDController() {
        return rightPIDController;
    }

    @Override
    public double getRightVolts() {
        return rightMaster.getAppliedOutput() * Constants.VOLT_LIMIT;
    }

    @Override
    public double getLeftVolts() {
        return leftMaster.getAppliedOutput() * Constants.VOLT_LIMIT;
    }

    @Override
    public void initMotorDirections() {}

}
