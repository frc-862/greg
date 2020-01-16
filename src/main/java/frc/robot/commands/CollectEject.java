/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;

public class CollectEject extends CommandBase {

  Collector mCollector;
  DoubleSupplier collectPwr;
  DoubleSupplier ejectPwr;

  /**
   * 
   * Creates a new Collect_Eject.
   */
  public CollectEject(Collector collector, DoubleSupplier collectPwr, DoubleSupplier ejectPwr) {

    mCollector = collector;
    this.collectPwr = collectPwr;
    this.ejectPwr = ejectPwr;

    addRequirements(mCollector);


    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    mCollector.setPower(collectPwr.getAsDouble() - ejectPwr.getAsDouble());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    super.end(interrupted);
    mCollector.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
