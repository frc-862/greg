/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.RobotMap;


public class IMU extends SubsystemBase {

    private final String name = "IMU";

    private final PigeonIMU bird;

    private double[] ypr = new double[3];

    public IMU() {
        setName(name);
        bird = new PigeonIMU(RobotMap.PIGEON_ID);
        bird.configFactoryDefault();
        reset();
    }

    public void init() {
    }

    public void periodic() {
        bird.getYawPitchRoll(ypr);
        SmartDashboard.putNumber("Angle Pigeon", getAngle());
    }

    public double getAngle() {
        return ((ypr[0] % 360)); // TODO - Doesnt work - do math 
    }

    public void reset() {
        bird.setYaw(0d);
    }

}


