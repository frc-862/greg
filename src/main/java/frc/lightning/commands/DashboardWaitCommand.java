/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DashboardWaitCommand extends CommandBase {
  private double time = 0d;
  private double startTime = 0d;
  private double targetTime = startTime + time;

  /**
   * Creates a new DashboardWaitCommand.
   */
  public DashboardWaitCommand() {
    // SmartDashboard.putNumber("AutoWaitSeconds", time); // time??
    // SmartDashboard.getEntry("AutoWaitSeconds");
    // time = SmartDashboard.getNumber("AutoWaitSeconds", 0d);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = SmartDashboard.getNumber("AutoWaitSeconds", 0d);
    System.out.println(time + "   <-----------------------TIME!!!!");
    startTime = Timer.getFPGATimestamp();
    targetTime = startTime + time;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() >= targetTime;
  }
}
