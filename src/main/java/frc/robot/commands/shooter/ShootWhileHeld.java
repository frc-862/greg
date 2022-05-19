/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LeadScrew;
import frc.robot.subsystems.Shooter;

public class ShootWhileHeld extends CommandBase {

  private Shooter shooter;
  private Indexer indexer;
  private LeadScrew leadscrew;

  /**
   * Creates a new FireThree.
   */
  public ShootWhileHeld(Shooter shooter, Indexer indexer, LeadScrew leadScrew) {
    this.shooter = shooter;
    this.indexer = indexer;
    this.leadscrew = leadScrew;
    addRequirements(shooter, indexer, leadScrew);

  }

  @Override
    public void initialize() {
      leadscrew.enableAutoAdjust();
      shooter.setShooterVelocity(shooter.getRPM());
      leadscrew.setAngle(shooter.getScrewAngle());
    }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(shooter.getScrewAngle() - leadscrew.getAngle()) <= 2) {
      indexer.safteyOpen();
      indexer.setPower(1);
      } //wait until the lead screw is at the right position
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setShooterVelocity(0d);
    shooter.stop();
    indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
