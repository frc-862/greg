/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {

  private final VictorSPX motor;

  /**
   * Creates a new Collector.
   */
  public Collector(VictorSPX motor) {
    this.motor = motor;
  }

  public void setPower(double pwr) {
    motor.set(ControlMode.PercentOutput, pwr);
  }

  public void stop() {
    setPower(0d);
  }

}
