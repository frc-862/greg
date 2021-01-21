/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.LightningConfig;
import frc.lightning.fault.FaultMonitor;
import frc.lightning.fault.FaultCode.Codes;
import frc.lightning.subsystems.IMU.IMUFunction;
import frc.lightning.util.LightningMath;
import frc.lightning.util.RamseteGains;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CTREDrivetrain extends SubsystemBase implements LightningDrivetrain {

	private final String name = "DRIVETRAIN";

	private BaseMotorController[] leftSlaves;
	private BaseMotorController leftMaster;

	private BaseMotorController[] rightSlaves;
	private BaseMotorController rightMaster;

	private DifferentialDriveKinematics kinematics;

	private DifferentialDriveOdometry odometry;

	private SimpleMotorFeedforward feedforward;

	private PIDController leftPIDController;

	private PIDController rightPIDController;

	private Pose2d pose = new Pose2d(0d, 0d, new Rotation2d());

	private Pose2d poseOffset = null;

	private RamseteGains gains;

	Supplier<Rotation2d> heading;

	IMUFunction zeroHeading;

	LightningConfig config;

	private int minAllowedEncoderVelocityError;

	protected CTREDrivetrain(LightningConfig config, BaseMotorController leftMaster, BaseMotorController rightMaster,
			BaseMotorController[] leftSlaves, BaseMotorController[] rightSlaves, Supplier<Rotation2d> heading, IMUFunction zeroHeading) {
		setName(name);
		this.leftMaster = leftMaster;
		this.rightMaster = rightMaster;
		this.leftSlaves = leftSlaves;
		this.rightSlaves = rightSlaves;

		this.gains = config.getRamseteGains();
		this.config = config;

		this.heading = heading;
		this.zeroHeading = zeroHeading;

		configureFollows();

		brake();
		init();

		kinematics = new DifferentialDriveKinematics(gains.getTrackWidth());

		odometry = new DifferentialDriveOdometry(heading.get(), pose);

		feedforward = new SimpleMotorFeedforward(gains.getkS(), gains.getkV(), gains.getkA());

		leftPIDController = new PIDController(gains.getLeft_kP(), gains.getLeft_kI(), gains.getLeft_kD());

		rightPIDController = new PIDController(gains.getRight_kP(), gains.getRight_kI(), gains.getRight_kD());

		resetSensorVals();

		// For now we will call our allowed tolerance 500% of the selected encoder's resolution
		minAllowedEncoderVelocityError = (int) (5 * config.getTicsPerRev());
		
        FaultMonitor.register(new FaultMonitor(Codes.LEFT_MOTORS_OUT_OF_SYNC, () -> getLeftMotorsOutOfSync(), "Left Motors Out Of Sync", true));
        FaultMonitor.register(new FaultMonitor(Codes.RIGHT_MOTORS_OUT_OF_SYNC, () -> getRightMotorsOutOfSync(), "Right Motors Out Of Sync", true));

		leftMaster.configOpenloopRamp(config.getOpenLoopRamp());
		leftMaster.configClosedloopRamp(config.getCloseLoopRamp());
		rightMaster.configOpenloopRamp(config.getOpenLoopRamp());
		rightMaster.configClosedloopRamp(config.getCloseLoopRamp());

	}

	@Override
	public void periodic() {
		super.periodic();
		pose = odometry.update(heading.get(), getLeftDistance(), getRightDistance());
	}

	protected BaseMotorController getLeftMaster() {
		return leftMaster;
	}

	protected BaseMotorController getRightMaster() {
		return rightMaster;
	}

	protected void withEachMotor(Consumer<BaseMotorController> op) {
		op.accept(leftMaster);
		for (var m : leftSlaves)
			op.accept(m);
		op.accept(rightMaster);
		for (var m : rightSlaves)
			op.accept(m);
	}

	protected void withEachMotorIndexed(BiConsumer<BaseMotorController, Integer> op) {
		op.accept(leftMaster, 0);
		op.accept(rightMaster, 0);
		for (var i = 0; i < leftSlaves.length; ++i) {
			op.accept(leftSlaves[i], i + 1);
		}
		for (var i = 0; i < rightSlaves.length; ++i) {
			op.accept(rightSlaves[i], i + 1);
		}
	}

	protected void withEachSlaveMotor(BiConsumer<BaseMotorController, BaseMotorController> op) {
		for (var m : leftSlaves)
			op.accept(m, leftMaster);
		for (var m : rightSlaves)
			op.accept(m, rightMaster);
	}

	protected void withEachSlaveMotorIndexed(BiConsumer<BaseMotorController, Integer> op) {
		for (var i = 0; i < leftSlaves.length; ++i) {
			op.accept(leftSlaves[i], i + 1);
		}
		for (var i = 0; i < rightSlaves.length; ++i) {
			op.accept(rightSlaves[i], i + 1);
		}
	}

	protected void withEachLeftSlave(Consumer<BaseMotorController> op) {
		for(var m : leftSlaves) op.accept(m);
	}

	protected void withEachRightSlave(Consumer<BaseMotorController> op) {
		for(var m : rightSlaves) op.accept(m);
	}

	private void configureFollows() {
		for (var m : leftSlaves)
			m.follow(getLeftMaster());
		for (var m : rightSlaves)
			m.follow(getRightMaster());
	}

	@Override
	public void initMotorDirections() {}

	@Override
	public void setPower(double left, double right) {
		leftMaster.set(ControlMode.PercentOutput, left);
		rightMaster.set(ControlMode.PercentOutput, right);
	}

	@Override
	public void setVelocity(double left, double right) {
		rightMaster.set(ControlMode.Velocity, left); // fps to talon?
		leftMaster.set(ControlMode.Velocity, right);
	}

	@Override
	public void resetDistance() {
		leftMaster.setSelectedSensorPosition(0);
		rightMaster.setSelectedSensorPosition(0);
	}

	@Override
	public double getLeftDistance() {
		return Units.feetToMeters(LightningMath.ticks2feet(leftMaster.getSelectedSensorPosition(), config.getWheelCircumferenceFeet(), config.getTicsPerRevWheel()));
	}

	@Override
	public double getRightDistance() {
		return Units.feetToMeters(LightningMath.ticks2feet(rightMaster.getSelectedSensorPosition(), config.getWheelCircumferenceFeet(), config.getTicsPerRevWheel()));
	}

	@Override
	public double getLeftVelocity() {
		return Units.feetToMeters(LightningMath.talon2fps(leftMaster.getSelectedSensorVelocity(), config.getWheelCircumferenceFeet(), config.getTicsPerRevWheel()));
	}

	@Override
	public double getRightVelocity() {
		return Units.feetToMeters(LightningMath.talon2fps(rightMaster.getSelectedSensorVelocity(), config.getWheelCircumferenceFeet(), config.getTicsPerRevWheel()));
	}

	@Override
	public void brake() {
		this.withEachMotor(m -> m.setNeutralMode(NeutralMode.Brake));
	}

	@Override
	public void coast() {
		this.withEachMotor(m -> m.setNeutralMode(NeutralMode.Coast));
	}

	public BaseMotorController[] getLeftMotors() {
		BaseMotorController[] motors = new BaseMotorController[leftSlaves.length + 1];
		motors[0] = getLeftMaster();
		for (int i = 1; i < motors.length; i++)
			motors[i] = leftSlaves[i - 1];
		return motors;
	}

	public BaseMotorController[] getRightMotors() {
		BaseMotorController[] motors = new BaseMotorController[rightSlaves.length + 1];
		motors[0] = getRightMaster();
		for (int i = 1; i < motors.length; i++)
			motors[i] = rightSlaves[i - 1];
		return motors;
	}

	public BaseMotorController getMotorByCANID(int id) {
		for(var m : getLeftMotors()) if(m.getDeviceID() == id) return m;
		for(var m : getRightMotors()) if(m.getDeviceID() == id) return m;
		return null;
	}

	@Override
	public DifferentialDriveKinematics getKinematics() {
		return kinematics;
	}

	@Override
	public Pose2d getPose() {
		return pose;
	}

	@Override
	public DifferentialDriveWheelSpeeds getSpeeds() {
		return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
	}

	@Override
	public void setOutput(double leftVolts, double rightVolts) {
		leftMaster.set(ControlMode.PercentOutput, (leftVolts / RobotController.getBatteryVoltage()));
		rightMaster.set(ControlMode.PercentOutput, (rightVolts / RobotController.getBatteryVoltage()));
	}

	@Override
	public PIDController getLeftPidController() {
		return leftPIDController;
	}

	@Override
	public PIDController getRightPidController() {
		return rightPIDController;
	}

	@Override
	public double getRightVolts() {
		return rightMaster.getMotorOutputVoltage();
	}

	@Override
	public double getLeftVolts() {
		return leftMaster.getMotorOutputVoltage();
	}

	@Override
	public void setRamseteOutput(double leftVolts, double rightVolts) {
		setOutput(leftVolts, rightVolts);
	}

	@Override
	public RamseteGains getConstants() {
		return gains;
	}

	@Override
	public void resetSensorVals() {
		resetDistance();
		zeroHeading.exec();
		odometry.resetPosition(new Pose2d(new Translation2d(0d, 0d), Rotation2d.fromDegrees(0d)), Rotation2d.fromDegrees(0d));
	}

	@Override
	public Pose2d getRelativePose() {
		return pose.relativeTo(poseOffset);
	}

	@Override
	public void setRelativePose() {
		poseOffset = pose;
	}

	public SimpleMotorFeedforward getFeedforward() {
		return feedforward;
	}

	@Override
	public double getRightTemp() {
		return rightMaster.getTemperature();
	}

	@Override
	public double getLeftTemp() {
		return leftMaster.getTemperature();
	}
	
	public boolean getLeftMotorsOutOfSync() {
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for(var motor : getLeftMotors()) {
			int encoder = motor.getSelectedSensorVelocity();
			if(encoder > max) max = encoder;
			if(encoder < min) min = encoder;
		}
		return (max - min) > minAllowedEncoderVelocityError;
	}

	public boolean getRightMotorsOutOfSync() {
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for(var motor : getRightMotors()) {
			int encoder = motor.getSelectedSensorVelocity();
			if(encoder > max) max = encoder;
			if(encoder < min) min = encoder;
		}
		return (max - min) > minAllowedEncoderVelocityError;
	}

}
