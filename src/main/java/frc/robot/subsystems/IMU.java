/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;


public class IMU extends SubsystemBase {
    private final String name = "IMU";

    public IMU() {
        setName(name);
    }

    public void init() {
    }

    public void periodic() {
        SmartDashboard.putNumber("Yaw", getYaw());
    }

    public double getYaw() {
        return 0;
    }
}


