/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.Climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualClimb extends CommandBase {
  final Climber climber;
  DoubleSupplier leftPwr; 
  DoubleSupplier rightPwr; 
  /**
   * Creates a new ManualClimb.
   */
  public ManualClimb(Climber climber, DoubleSupplier leftPwr, DoubleSupplier rightPwr) {
    this.climber = climber;
    this.rightPwr = rightPwr; 
    this.leftPwr = leftPwr;
    
    addRequirements(climber);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climber.setPwr(leftPwr.getAsDouble(), rightPwr.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.setPwr(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
