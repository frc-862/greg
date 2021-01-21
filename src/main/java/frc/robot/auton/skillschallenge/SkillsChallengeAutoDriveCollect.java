/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton.skillschallenge;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.auto.Path;
import frc.robot.commands.Collect;
import frc.robot.commands.IndexerCommand;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

/**
 * Drives given path while collecting and indexing.
 */
public class SkillsChallengeAutoDriveCollect extends SequentialCommandGroup {

  private static final double COLLECT_PWR = 1d;

  private final double WAIT_TIME = 0d;

  private static SkillsChallengePathGenerator pathGenerator = new SkillsChallengePathGenerator();

  private double initTime = 0d;

  private double duration;

  private LightningDrivetrain drivetrain;

  private Collector collector;

  private Indexer indexer;

  public SkillsChallengeAutoDriveCollect(LightningDrivetrain drivetrain, Collector collector, Indexer indexer, Path path) {

    super(
      new InstantCommand(indexer::safteyClosed, indexer),
      new ParallelCommandGroup(
        new Collect(collector, () -> COLLECT_PWR),
        new IndexerCommand(indexer), 
        path.getCommand(drivetrain)
      )
    );

    this.drivetrain = drivetrain;
    this.collector = collector;
    this.indexer = indexer;

    duration = path.getDuration(drivetrain);

  }

  @Override
  public void initialize() {
    super.initialize();
    initTime = Timer.getFPGATimestamp();
  }

  @Override
  public boolean isFinished() { return (Timer.getFPGATimestamp() - initTime) > (duration + WAIT_TIME); }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    indexer.stop();
    collector.stop();
    // collector.puterOuterOut();
    drivetrain.stop();
  }
  
}
