/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  // Components

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    // Init
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double pwr) {

  }

  public void stop(){
    setPower(0d);
  }

  public void setFlywheelVelocity(double vel) {

  }

  public double getFlywheelVelocity() {
    return 0d;
  }

  public void aim() {
    // position robot & other based on vision values
  }

}
