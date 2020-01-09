/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.subsystems.LightningDrivetrain;

public class NebulaDrivetrain extends SubsystemBase implements LightningDrivetrain {

  private final TalonSRX leftMaster;
  private final TalonSRX rightMaster;
  private final VictorSPX leftSlave;
  private final VictorSPX rightSlave;

  /**
   * Creates a new NebulaDrivetrain.
   */
  public NebulaDrivetrain() {

    leftMaster = new TalonSRX(0);
    rightMaster = new TalonSRX(15);
    leftSlave = new VictorSPX(1);
    rightSlave = new VictorSPX(14);

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

  }

  public void setPower(final double left, final double right) {
    leftMaster.set(ControlMode.PercentOutput, left);
    rightMaster.set(ControlMode.PercentOutput, right);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void setVelocity(double left, double right) {
    // TODO Auto-generated method stub

  }

  @Override
  public void resetDistance() {
    // TODO Auto-generated method stub

  }

  @Override
  public double getLeftDistance() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getRightDistance() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getLeftVelocity() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getRightVelocity() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public DifferentialDrive getDifferentialDrive() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void brake() {
    // TODO Auto-generated method stub

  }

  @Override
  public void coast() {
    // TODO Auto-generated method stub

  }
}
