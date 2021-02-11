/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.auto.Path;
import frc.robot.commands.Collect;
import frc.robot.commands.FullAutoFireMagazine;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.shooter.FireThree;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.ShooterAngle;

/**
 * Drives given path while collecting and indexing.
 */
public class InterStellarAccuracyCommand extends SequentialCommandGroup {

    private static final double COLLECT_PWR = 1d;

    private static final double WAIT_TIME = 0d;

    private LightningDrivetrain drivetrain;

    private Collector collector;

    private Shooter shooter;

    private Indexer indexer;

    private double initTime = 0d;

    private double duration;

  public InterStellarAccuracyCommand(LightningDrivetrain drivetrain, Collector collector, Indexer indexer, Shooter shooter, ShooterAngle shooterAngle, Vision vision, Path shootPath, Path returnPath) {
    super(
        new InstantCommand(indexer::safteyClosed, indexer),
        new ParallelCommandGroup(
          new Collect(collector, () -> COLLECT_PWR),
          new IndexerCommand(indexer),
          new CommandBase(){
            @Override
            public boolean isFinished(){
                return indexer.getBallCount() >= 3;
            }
          }
        )
      );
  
    if(shootPath != null){ super.addCommands(shootPath.getCommand(drivetrain)); }

    Command[] commandArray = new Command[]{ 
        new CommandBase(){
            @Override
            public boolean isFinished(){
                return indexer.getBallCount() >= 3;
            }
        },
        new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer),
        new InstantCommand(() -> initTime = Timer.getFPGATimestamp()),
        returnPath.getCommand(drivetrain)}; 

    super.addCommands(commandArray); 

    this.drivetrain = drivetrain;
    this.collector = collector;
    this.indexer = indexer;
    this.shooter = shooter;

    duration = returnPath.getDuration(drivetrain);
    indexer.setBallsHeld();
  }

  @Override
  public void initialize() {
    super.initialize();

  }

  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - initTime) > (duration + WAIT_TIME);
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    indexer.stop();
    collector.stop();
    drivetrain.stop();
  }
  
}
