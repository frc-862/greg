/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.commands.Collect;
import frc.robot.commands.Index;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

/**
 * Drives given path while collecting and indexing.
 */
public class AutoDriveCollect extends ParallelCommandGroup {

  private static final double COLLECT_PWR = 1d;

  private final double WAIT_TIME = 0.5d;

  private static PathGenerator pathGenerator = new PathGenerator();

  private double initTime = 0d;

  private double duration;

  public AutoDriveCollect(LightningDrivetrain drivetrain, Collector collector, Indexer indexer, PathGenerator.Paths path) {

    super(
      new Collect(collector, () -> COLLECT_PWR),
      new Index(indexer), 
      pathGenerator.getRamseteCommand(drivetrain, path)
    );

    duration = pathGenerator.getDuration(drivetrain, path);

  }

  @Override
  public void initialize() {
    super.initialize();
    initTime = Timer.getFPGATimestamp();
  }

  @Override
  public boolean isFinished() { return (Timer.getFPGATimestamp() - initTime) > (duration + WAIT_TIME); }
  
}
