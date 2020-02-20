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
import frc.robot.subsystems.leds.LEDs;

public class SolidRed extends CommandBase {

  LEDs leds;
  IntSupplier square;
  double timerAtor = Timer.getFPGATimestamp();
  boolean toggleForTimer = false;
  /**
   * Creates a new BlinkGreen.
   */
  public SolidRed(IntSupplier square, LEDs leds) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.leds = leds;
    this.square = square;
    addRequirements(leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (square.getAsInt() == 2) {
      square = () -> 9;
    }
    if (square.getAsInt() == 3) {
      square = () -> 17;
    }
    if (square.getAsInt() == 4) {
      square = () -> 25;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    leds.redMatrix(square.getAsInt());
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leds.stop();
    leds.clearMatrix(square.getAsInt());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
