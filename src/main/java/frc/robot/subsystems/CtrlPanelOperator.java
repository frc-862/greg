/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CtrlPanelOperator extends SubsystemBase {

  public enum Colors {
    RED("R", 1),
    GREEN("G", 2),
    BLUE("B", 3),
    YELLOW("Y", 4);

    final String FMSVal;
    final int order;

    private Colors(String FMSVal, int order){
      this.FMSVal = FMSVal;
      this.order = order;
    }

  }

  //Components

  /**
   * Creates a new CtrlPanelOperator.
   */
  public CtrlPanelOperator() {
    //Init
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * spin 3-5 times
   */
  public void spin() {

  }

  /**
   * position to fms deginated color
   */
  public void position() {

  }

  private String getFMSMsg() {
    return null;
  }

  private String getCurrentColor() {
    return null;
  }

}
