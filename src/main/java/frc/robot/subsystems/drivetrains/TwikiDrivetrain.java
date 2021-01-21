/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.RamseteGains;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;
import frc.lightning.subsystems.NeoDrivetrain;

public class TwikiDrivetrain extends NeoDrivetrain {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID = 4;
    public static final int RIGHT_1_CAN_ID = 1;

    Encoder rightEncoder = new Encoder(0, 1);

    public TwikiDrivetrain() {
       // super(1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, Constants.TWIKI);
        super(null, 1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, null, null);
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
