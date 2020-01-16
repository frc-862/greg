/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class Fire extends CommandBase {

  Shooter shooter;
  Indexer indexer;
  DoubleSupplier flyWheelSpeed;
  BooleanSupplier flyWheelAtVelocity;

  /**
   * Creates a new Fire.
   */
  public Fire(Shooter shooter, Indexer indexer, DoubleSupplier flyWheelSpeed, BooleanSupplier flyWheelAtVelocity) {

    this.shooter = shooter;
    this.indexer = indexer;
    this.flyWheelSpeed = flyWheelSpeed;
    this.flyWheelAtVelocity = flyWheelAtVelocity;

    addRequirements(shooter);

    // Use addRequirements() here to declare subsystem dependencies...
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    shooter.setFlywheelVelocity(0d);
    if (flyWheelAtVelocity.getAsBoolean()){
      indexer.feed();
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  
    super.end(interrupted);
    shooter.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
