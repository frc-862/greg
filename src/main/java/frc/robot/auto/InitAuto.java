/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Vision;

public class InitAuto extends InstantCommand {

  private Vision vision;
  private Indexer indexer;
  private Collector collector;

  public InitAuto(Vision vision, Indexer indexer, Collector collector) {
    this.vision = vision;
    this.indexer = indexer;
    this.collector = collector;
    addRequirements(vision, indexer, collector);
  }

  @Override
  public void initialize() {
    vision.bothRingsOn();
    indexer.setBallsHeld();
    collector.puterOuterIn();
  }

}
