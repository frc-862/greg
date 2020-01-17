/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Indexer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class IndexOutAll extends CommandBase {

  Indexer mIndex; 
  DoubleSupplier feedoutPwr;

  /**
   * Creates a new IndexAll.
   */
  public IndexOutAll(Indexer index, DoubleSupplier feedoutPwr) {

    mIndex = index;
    this.feedoutPwr = feedoutPwr;

    addRequirements(mIndex);

    // Use addRequirements() here to declare subsystem dependencies.
   }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    mIndex.setPower(0d);
    

   // mIndex.setPower( -feedoutPwr.getAsDouble());
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    mIndex.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
