/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.testing;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class Verify extends InstantCommand {

  private boolean isVerified;

  public Verify() {
    isVerified = false;
  }

  @Override
  public void initialize() {
    isVerified = true;
  }

  public void reset() {
    isVerified = false;
  }

  public boolean isVerified() { return isVerified; }

}
