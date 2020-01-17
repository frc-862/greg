/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CtrlPanelOperator extends SubsystemBase {

  public enum Colors {
    NO_COLOR("NA", 0),
    RED("MY", 1),
    GREEN("CY", 2),
    BLUE("C", 3),
    YELLOW("Y", 4);

    final String FMSVal;
    final int order; 

    private Colors(String FMSVal, int order){
      this.FMSVal = FMSVal;
      this.order = order;
    }

  }

  private Colors color = Colors.NO_COLOR;
  
  //Components
  public final ColorSensorV3 colorSensor;


  /**
   * Creates a new CtrlPanelOperator.
   */
  public CtrlPanelOperator() {
    //Init
    colorSensor = new ColorSensorV3(Port.kOnboard);
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
    color = getCurrentColor(); 
    
  }

  /**
   * spin 3-5 times
   */

 

  /**
   * position to fms deginated color
   */
  public void Position(String Position) {
   
    

  }

  private String getFMSMsg() {
    return null;
  }

  private Colors getCurrentColor() {
    return null;
  }

  public void setpwr(double pwr) {
  
  }

  public void spin(double pwr, double time) {
    //set power blah blah for blank time
     setpwr(pwr);
  
      }

  public void stop() {
    setpwr(0d);
  }

}
