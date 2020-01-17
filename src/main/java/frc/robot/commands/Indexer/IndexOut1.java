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

public class IndexOut1 extends CommandBase {

  Indexer indexout;
  DoubleSupplier indexoutPwr;
  Boolean BeamBrake;


  /**
   * Creates a new Index1.
   */
  public IndexOut1(IndexOut1 index, DoubleSupplier indexoutPwr, Boolean BeamBrake) {

    index = index;
    this.BeamBrake = BeamBrake;
    this.indexoutPwr = indexoutPwr;

    addRequirements(indexout);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    indexout.setPower(-1d);
    if(BeamBrake);
    indexout.setPower(0d);
    
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    indexout.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
