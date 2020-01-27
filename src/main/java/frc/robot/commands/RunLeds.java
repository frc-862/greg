/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LED;

public class RunLeds extends CommandBase {
  private final LED LEDs;
  /**
   * Creates a new RunLeds.
   */
  public RunLeds(LED LEDs) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.LEDs = LEDs;
    addRequirements(LEDs);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    LEDs.cycle();
    //LEDs.end();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    LEDs.end();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
