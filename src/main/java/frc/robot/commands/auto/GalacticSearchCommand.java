/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.auto.Path;
import frc.lightning.auto.Paths;
import frc.robot.commands.Collect;
import frc.robot.commands.IndexerCommand;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

/**
 * Drives given path while collecting and indexing.
 */
public class GalacticSearchCommand extends CommandBase {

  private final String nullState = "NONE";

  private LightningDrivetrain drivetrain;

  private NetworkTableEntry pathName;

  private Collector collector;

  private Indexer indexer;

  public GalacticSearchCommand(LightningDrivetrain drivetrain, Collector collector, Indexer indexer) {
    final var vision_tab = Shuffleboard.getTab("Vision");
    vision_tab.addBoolean("DeterminePath", this::isPathNull);

    this.drivetrain = drivetrain;
    this.collector = collector;
    this.indexer = indexer;

    pathName = vision_tab.add("DeterminedPath", nullState)
                .withWidget(BuiltInWidgets.kTextView)
                .getEntry();
  }

  private boolean isPathNull(){
      if(pathName != null) {
        return pathName.getString(nullState).equals(nullState);
      }
      return false;
  }

  @Override
  public void initialize() {
    super.initialize();

  }

  @Override
  public boolean isFinished() {
    return !isPathNull();
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    Path path = Paths.getPath(pathName.getString(nullState));
    (new CollectPathCommand(drivetrain, collector, indexer, path)).schedule();
   
  }
  
}
