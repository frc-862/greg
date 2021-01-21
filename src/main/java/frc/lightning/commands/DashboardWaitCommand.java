/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.Map;

public class DashboardWaitCommand extends CommandBase {
  private NetworkTableEntry entry;
  private double targetTime;

  /**
   * Creates a new DashboardWaitCommand.
   */
  public DashboardWaitCommand() { this("AutoWaitSeconds"); }
  public DashboardWaitCommand(String key) { this(key, "Autonomous"); }
  public DashboardWaitCommand(String key, String tab_name) {
    final var tab = Shuffleboard.getTab(tab_name);

    for (final var elem : tab.getComponents()) {
      if (elem.getTitle().equals(key) && elem instanceof SimpleWidget) {
        entry = ((SimpleWidget) elem).getEntry();
      }
    }

    if (entry == null) {
      entry = Shuffleboard.getTab(tab_name)
              .add(key, 0d)
              .withWidget(BuiltInWidgets.kNumberSlider)
              .withProperties(Map.of("min", 0, "max", 15)) // specify widget properties here
              .getEntry();
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    final double time = (entry != null) ? entry.getDouble(0) : 0;
    System.out.println(time + "   <-----------------------TIME!!!!");
    final double startTime = Timer.getFPGATimestamp();
    targetTime = startTime + time;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() >= targetTime;
  }
}
