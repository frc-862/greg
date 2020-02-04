/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PrototypeCollector;

public class PrototypeCollectEject extends CommandBase {
  
  PrototypeCollector collector;
  DoubleSupplier collectPwr, ejectPwr;
  
  public PrototypeCollectEject(PrototypeCollector collector, DoubleSupplier collectPwr, DoubleSupplier ejectPwr) {
    this.collector = collector;
    this.collectPwr = collectPwr;
    this.ejectPwr = ejectPwr;
    addRequirements(collector);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    collector.setPower(collectPwr.getAsDouble() - ejectPwr.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    collector.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
