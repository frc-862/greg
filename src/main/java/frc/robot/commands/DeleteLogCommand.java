// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.File;
import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DeleteLogCommand extends CommandBase {

  private int filesToKeep;

  private boolean isDone;

  File log_folder = new File("/home/lvuser/log");
  File logFiles[] = log_folder.listFiles();
  
  public DeleteLogCommand(int filesToKeep) {
    this.filesToKeep = filesToKeep;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("logFileCount", getLogAmount());
    Arrays.sort(logFiles);

    if (logFiles.length > filesToKeep) {
      int overcount = logFiles.length - filesToKeep;
      
      for (int i = 0; i < overcount; i++) {
        logFiles[i].delete();
      }

      logFiles= log_folder.listFiles();

      for (int i = 0; i < filesToKeep; i++) {
        logFiles[i].renameTo(new File(String.format("/home/lvuser/log/%s-%05d-dl.log", "greg", i + 1)));
      }

      isDone = true;
    }
  }

  public int getLogAmount() {
    return logFiles.length;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isDone;
  }
}
