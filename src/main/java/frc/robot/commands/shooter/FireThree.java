/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class FireThree extends CommandBase {

  private Shooter shooter;
  private Indexer indexer;
  private ShooterAngle shooterAngle;
  private Vision vision;
  private Collector collector;
  
  private double time;

  /**
   * Creates a new FireThree.
   */
  public FireThree(Shooter shooter, Indexer indexer, ShooterAngle shooterAngle, Vision vision, Collector collector) {
    this.shooter = shooter;
    this.indexer = indexer;
    this.shooterAngle = shooterAngle;
    this.vision = vision;
    this.collector = collector;
    addRequirements(shooter, indexer, shooterAngle, vision, collector);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setShooterVelocity(2500);
    shooterAngle.setAngle(32);
    time = Timer.getFPGATimestamp();
    indexer.safteyOpen();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    indexer.toShooter();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("I am ending " + interrupted);
    shooter.setShooterVelocity(0d);
    shooter.stop();
    indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ((Timer.getFPGATimestamp() - time) > 4d);
  }
}
