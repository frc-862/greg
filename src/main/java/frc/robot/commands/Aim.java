/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.subsystems.Vision;

public class Aim extends CommandBase {
  LightningDrivetrain drivetrain;
  Vision vision;
  DoubleSupplier drivePwr;

  /**
   * Creates a new Aim.
   */
  public Aim(LightningDrivetrain drivetrain, Vision vision, DoubleSupplier drivePwr) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {



  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //drivetrain.setPower(cmd);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
