/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robots.EddieContainer;
import frc.robot.subsystems.LEDs;

public class RunLeds extends CommandBase {
  /**
   * Creates a new RunLeds.
   */
  public RunLeds() {
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    EddieContainer.Leds.clearBuffer();
    EddieContainer.Leds.setLED2Buffer();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println(toggle);
    if (EddieContainer.Leds.toggle){
      EddieContainer.Leds.rainbow();
      //EddieContainer.Leds.updateBuffer(0, 50, 0);
      //EddieContainer.Leds.green();
      EddieContainer.Leds.setLED2Buffer();
    }
    else {
      EddieContainer.Leds.updateBuffer(0, 0, 0);
      EddieContainer.Leds.setLED2Buffer();
    }

    //EddieContainer.Leds.rainbow();
      //EddieContainer.Leds.updateBuffer(0, 50, 0);
      //EddieContainer.EddieContainer.Leds.green();
      //EddieContainer.EddieContainer.Leds.setLED2Buffer();
      //EddieContainer.EddieContainer.Leds.setFour();
      //EddieContainer.EddieContainer.Leds.setThree();
      if(EddieContainer.Leds.toggleA){EddieContainer.Leds.setA(); }
      else if(!EddieContainer.Leds.toggleA){EddieContainer.Leds.clearA();}

      if(EddieContainer.Leds.toggleB){EddieContainer.Leds.setB(); }
      else if(!EddieContainer.Leds.toggleB){EddieContainer.Leds.clearB();}

      if(EddieContainer.Leds.toggleC){EddieContainer.Leds.setC();}
      else if(!EddieContainer.Leds.toggleC){EddieContainer.Leds.clearC();}

      if(EddieContainer.Leds.toggleD){EddieContainer.Leds.setD();}
      else if(!EddieContainer.Leds.toggleD){EddieContainer.Leds.clearD();}
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    EddieContainer.Leds.stop();
    //LEDs.clearBuffer();
    //LEDs.setLED2Buffer();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
