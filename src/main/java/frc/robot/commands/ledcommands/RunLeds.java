/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

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
    //init codes
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //if(EddieContainer.Leds.toggleA){EddieContainer.Leds.setA(); }
      //else if(!EddieContainer.Leds.toggleA){EddieContainer.Leds.clearA();}

      //if(EddieContainer.Leds.toggleB){EddieContainer.Leds.setB(); }
      //else if(!EddieContainer.Leds.toggleB){EddieContainer.Leds.clearB();}

      //if(EddieContainer.Leds.toggleC){EddieContainer.Leds.setC();}
      //else if(!EddieContainer.Leds.toggleC){EddieContainer.Leds.clearC();}

      //if(EddieContainer.Leds.toggleD){EddieContainer.Leds.setD();}
      //else if(!EddieContainer.Leds.toggleD){EddieContainer.Leds.clearD();}
      //EddieContainer.Leds.rainbow();
      System.out.println("If you are seeing this, the command is being called");
      EddieContainer.Leds.setA();
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
