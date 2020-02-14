/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

import java.util.function.IntSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDs;

public class BlinkGreen extends CommandBase {

  LEDs leds;
  IntSupplier square;
  /**
   * Creates a new BlinkGreen.
   */
  public BlinkGreen(IntSupplier square) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.square = square;
    addRequirements(leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    boolean toggleForTimer = true;
    double timerAtor = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    boolean toggleForTimer;
    double timerAtor;
    if ((Timer.getFPGATimestamp() - timerAtor) >= 1.0) { 
      if (toggleForTimer) {
        leds.greenMatrix(square.getAsInt());
        toggleForTimer = false;
      }
      else {
        leds.clearMatrix(square.getAsInt());
        toggleForTimer = true;
      }
      timerAtor = Timer.getFPGATimestamp();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
