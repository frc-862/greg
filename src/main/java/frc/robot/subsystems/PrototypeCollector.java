/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PrototypeCollector extends SubsystemBase {

  // Victor
  // VictorSP 
  private VictorSP linearMotor;
  private VictorSP longitudinalMotor;

  public PrototypeCollector() {
    longitudinalMotor = new VictorSP(0);
    linearMotor = new VictorSP(1);
  }

  public void setPower(double pwr) {
    longitudinalMotor.set(pwr);
    linearMotor.set(pwr);
  }

  public void stop() {
    setPower(0d);
  }

}
