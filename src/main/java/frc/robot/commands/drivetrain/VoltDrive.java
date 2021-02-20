/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.JoystickFilter;
import frc.robot.Constants;

public class VoltDrive extends CommandBase {

  LightningDrivetrain drivetrain;
  DoubleSupplier left;
  DoubleSupplier right;
  private double deadband = 0.15;
  private double minPower = 0.1;
  private double maxPower = 1.0;
  private final JoystickFilter filter = new JoystickFilter(deadband, minPower, maxPower, JoystickFilter.Mode.CUBED);

  public VoltDrive(LightningDrivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
    this.drivetrain = drivetrain;
    this.left = left;
    this.right = right;
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftVolts = filter.filter(left.getAsDouble());
    double rightVolts = filter.filter(right.getAsDouble());

    leftVolts *= Constants.VOLT_LIMIT;
    rightVolts *= Constants.VOLT_LIMIT;

    drivetrain.setOutput(leftVolts, rightVolts);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    drivetrain.stop();
  }

}
