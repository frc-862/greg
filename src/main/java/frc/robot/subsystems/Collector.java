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

  //Components

  VictorSPX drive1, drive2;

  /**
   * Creates a new Collector.
   */
  public Collector() {
    // Init
    drive1 = new VictorSPX(9);
    drive2 = new VictorSPX(10);
    
    drive1.setInverted(false);
    drive2.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void deployGround() {

  }

  public void deployPort() {

  }

  public void retract() {

  }

  public void collect() {
    collect(1d);
  }

  public void eject() {
    eject(-1d);
  }

  
  public void collect(double pwr) {

  }

  public void eject(double pwr) {

  }

  public void setPower(double pwr) {
    drive1.set(ControlMode.PercentOutput, pwr);
    drive2.set(ControlMode.PercentOutput, pwr);
  }

  public void stop() {
    setPower(0d);
  }

}
