/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;

public class Collect extends CommandBase {

  private final Collector collector;
  private final DoubleSupplier leftTrigger;
  private final DoubleSupplier rightTrigger;

  /**
   * Creates a new Collect.
   */
  public Collect(Collector collector, DoubleSupplier leftTrigger, DoubleSupplier rightTrigger) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.collector = collector;
    this.leftTrigger = leftTrigger;
    this.rightTrigger = rightTrigger;

    addRequirements(collector);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    collector.setPower(rightTrigger.getAsDouble() - leftTrigger.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    collector.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
