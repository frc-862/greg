/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.lightning.subsystems.CTREDriveTrain;

public class NebulaDrivetrain extends CTREDriveTrain {

  public static NebulaDrivetrain create() {
    VictorSPX[] left = { new VictorSPX(1) };
    VictorSPX[] right = { new VictorSPX(14) };

    return new NebulaDrivetrain(new TalonSRX(0), new TalonSRX(15), left, right);
  }

  public NebulaDrivetrain(TalonSRX leftMaster, TalonSRX rightMaster, BaseMotorController[] left, BaseMotorController[] right) {
    super(leftMaster, rightMaster, left, right);
    rightMaster.setInverted(true);
    for (var rmotor : right) rmotor.setInverted(false);//true
    leftMaster.setInverted(false);
    for (var lmotor : left) lmotor.setInverted(true);//false
  }
}
