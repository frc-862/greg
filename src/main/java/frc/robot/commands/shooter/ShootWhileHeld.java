/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LeadScrew;
import frc.robot.subsystems.Shooter;

public class ShootWhileHeld extends CommandBase {

  private Shooter shooter;
  private Indexer indexer;
  private LeadScrew leadscrew;
  private Collector collector;

  /**
   * Creates a new FireThree.
   */
  public ShootWhileHeld(Shooter shooter, Indexer indexer, LeadScrew leadScrew, Collector collector) {
    this.shooter = shooter;
    this.indexer = indexer;
    this.leadscrew = leadScrew;
    this.collector = collector;
    addRequirements(shooter, indexer, leadScrew, collector);
  }

  @Override
    public void initialize() {
        
  

      leadscrew.enableAutoAdjust();
      shooter.setShooterVelocity(SmartDashboard.getNumber("RPM", 2000));
      leadscrew.setAngle(SmartDashboard.getNumber("angle", 39));
      indexer.safteyOpen();
      indexer.setPower(1);

    }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    indexer.toShooter();
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
