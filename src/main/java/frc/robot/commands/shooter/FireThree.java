/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

public class FireThree extends CommandBase {

  private LightningDrivetrain dt;
  private Shooter shooter;
  private Indexer indexer;
  private ShooterAngle shooterAngle;
  private Vision vision;
  
  private double time;

  /**
   * Creates a new FireThree.
   */
  public FireThree(LightningDrivetrain dt, Shooter shooter, Indexer indexer, ShooterAngle shooterAngle, Vision vision) {
    this.dt = dt;
    this.shooter = shooter;
    this.indexer = indexer;
    this.shooterAngle = shooterAngle;
    this.vision = vision;
    addRequirements(dt, shooter, indexer, shooterAngle, vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setShooterVelocity(vision.getBestShooterVelocity());
    // shooterAngle.setShooterAngle(vision.getBestShooterAngle());
    time = Timer.getFPGATimestamp();
    indexer.safteyClosed();
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
    indexer.ballCount -= 3; // shot 3 balls
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;//((Timer.getFPGATimestamp() - time) > 5d);
  }
}
