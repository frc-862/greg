/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import java.util.function.Supplier;

import com.revrobotics.*;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.config.TwikiConfig;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.subsystems.IMU.IMUFunction;

public class TwikiDrivetrain extends NeoDrivetrain {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID = 4;
    public static final int RIGHT_1_CAN_ID = 1;

    Encoder rightEncoder = new Encoder(0, 1);

    public TwikiDrivetrain(Supplier<Rotation2d> heading, IMUFunction zeroHeading) {
        super(new TwikiConfig(), 1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, heading, zeroHeading);
        initMotorDirections();
    }

    @Override
    public void initMotorDirections() {
        getRightMaster().setInverted(true);
        getLeftMaster().setInverted(false);
    }

    public void unfollow() {
        CANSparkMax.ExternalFollower f = new CANSparkMax.ExternalFollower(0,0);
        getLeftMaster().follow(f, 0);
    }
    
}