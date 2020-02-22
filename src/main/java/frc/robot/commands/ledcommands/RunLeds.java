/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.leds.LEDs;


public class RunLeds extends CommandBase {
  /**
   * Creates a new RunLeds.
   */
  LEDs leds;

  public RunLeds(LEDs leds) {
    this.leds = leds;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //init codes
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    leds.TogglePhase(1);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leds.stop();
    //LEDs.clearBuffer();
    //LEDs.setLED2Buffer();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
