/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.robots.GregContainer;

public class Indexer extends SubsystemBase {

  private final double pwrThreshold = 0.05;

  // Components

  /**
   * Creates a new Indexer.
   */
  public Indexer() {
    // Init
  }

  @Override
  public void periodic() {
    updateBallCount();
  }

  public void feed() {
    
  }
  
  public void setPower(double pwr) {
  
  }

  /**
   * if ball passes through beam break and indexer moving forward, then add ball
   * if ball passes through beam break and indexer moving backward, then bye bye ball
   * else do nothing - ideling
   */
  private void updateBallCount() {
    if(getBeamBreak() && (getPower() > pwrThreshold)) GregContainer.setPowerCellCapacity(GregContainer.getPowerCellCapacity() + 1);
    else if (getBeamBreak() && (getPower() < -pwrThreshold)) GregContainer.setPowerCellCapacity(GregContainer.getPowerCellCapacity() - 1);
  }

  private boolean getBeamBreak() {
    return false;
  }

  public double getPower() {
    return 0d;
  }

  public void stop(){

      setPower(0d);

  }



}
