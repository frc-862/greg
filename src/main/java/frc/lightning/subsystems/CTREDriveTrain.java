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

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.RamseteGains;
import frc.robot.Robot;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

public class CTREDriveTrain extends SubsystemBase implements LightningDrivetrain {
    private static final double CLOSE_LOOP_RAMP_RATE = 0.5;
    private static final double OPEN_LOOP_RAMP_RATE = 0.5;

    // DRIVETRAIN
    private final String name = "DRIVETRAIN";

  private BaseMotorController[] leftSlaves;
  private TalonSRX leftMaster;
  // private CANEncoder leftEncoder;
  // private CANPIDController leftPIDFController;

  private BaseMotorController[] rightSlaves;
  private TalonSRX rightMaster;
  // private CANEncoder rightEncoder;
  // private CANPIDController rightPIDFController;

  public CTREDriveTrain(TalonSRX leftMaster, TalonSRX rightMaster, BaseMotorController[] leftSlaves,
      BaseMotorController[] rightSlaves) {
    setName(name);
    this.leftMaster = leftMaster;
    this.rightMaster = rightMaster;
    this.leftSlaves = leftSlaves;
    this.rightSlaves = rightSlaves;

    configureFollows();

    brake();
    init();
  }

  public void init() {
    this.resetDistance();
  }

  protected TalonSRX getLeftMaster() {
    return leftMaster;
  }

  protected TalonSRX getRightMaster() {
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

  protected void withEachSlaveMotor(BiConsumer<BaseMotorController, TalonSRX> op) {
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

  private void configureFollows() {
    for (var m : leftSlaves)
      m.follow(getLeftMaster());
    for (var m : rightSlaves)
      m.follow(getRightMaster());
  }

  @Override
  public void initMotorDirections() {
  }

  public void setPower(double left, double right) {
    rightMaster.set(ControlMode.PercentOutput, left);
    leftMaster.set(ControlMode.PercentOutput, right);
  }

  public void setVelocity(double left, double right) {
    rightMaster.set(ControlMode.Velocity, left);
    leftMaster.set(ControlMode.Velocity, right);
  }

  public void resetDistance() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  public double getLeftDistance() {
    return leftMaster.getSelectedSensorPosition();
  }

  public double getRightDistance() {
    return rightMaster.getSelectedSensorPosition();
  }

  public double getLeftVelocity() {
    return leftMaster.getSelectedSensorVelocity();
  }

  public double getRightVelocity() {
    return rightMaster.getSelectedSensorVelocity();
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
  public void setOutput(double leftVolts, double rightVolts) {
    // TODO Auto-generated method stub

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

  @Override
  public double getRightVolts() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getLeftVolts() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setRamseteOutput(double leftVolts, double rightVolts) {
    // TODO Auto-generated method stub

  }

  @Override
  public RamseteGains getConstants() {
    // Override me!
    return null;
  }

  @Override
  public void resetSensorVals() {
    // TODO Auto-generated method stub

  }

  @Override
  public Pose2d getRelativePose() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setRelativePose() {
    // TODO Auto-generated method stub

  }
}
