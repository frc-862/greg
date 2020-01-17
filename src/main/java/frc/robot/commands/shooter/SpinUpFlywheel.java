/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SpinUpFlywheel extends CommandBase {

  Shooter shooter;
  DoubleSupplier pwr;

  /**
   * Creates a new Fire.
   */
  public SpinUpFlywheel(Shooter shooter, DoubleSupplier pwr) {

    this.shooter = shooter;
    this.pwr = pwr;

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

    shooter.setPower(pwr.getAsDouble());

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
