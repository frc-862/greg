/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDs;

public class RunLeds extends CommandBase {
  private final LEDs LEDs;
  /**
   * Creates a new RunLeds.
   */
  public RunLeds(LEDs LEDs) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.LEDs = LEDs;
    addRequirements(LEDs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    LEDs.clearBuffer();
    LEDs.setLED2Buffer();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println(toggle);
    if (LEDs.toggle){
      LEDs.rainbow();
      //LEDs.updateBuffer(0, 50, 0);
      //LEDs.green();
      LEDs.setLED2Buffer();
    }
    else {
      LEDs.updateBuffer(0, 0, 0);
      LEDs.setLED2Buffer();
    }

    //LEDs.rainbow();
      //LEDs.updateBuffer(0, 50, 0);
      //EddieContainer.Leds.green();
      //EddieContainer.Leds.setLED2Buffer();
      //EddieContainer.Leds.setFour();
      //EddieContainer.Leds.setThree();
      if(LEDs.toggleA){LEDs.setA(); }
      else if(!LEDs.toggleA){LEDs.clearA();}

      if(LEDs.toggleB){LEDs.setB(); }
      else if(!LEDs.toggleB){LEDs.clearB();}

      if(LEDs.toggleC){LEDs.setC();}
      else if(!LEDs.toggleC){LEDs.clearC();}

      if(LEDs.toggleD){LEDs.setD();}
      else if(!LEDs.toggleD){LEDs.clearD();}
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    LEDs.stop();
    //LEDs.clearBuffer();
    //LEDs.setLED2Buffer();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
