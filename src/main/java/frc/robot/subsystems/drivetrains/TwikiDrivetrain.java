/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.NeoDrivetrain;

public class TwikiDrivetrain extends NeoDrivetrain implements LightningDrivetrain {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID  = 1;
    public static final int RIGHT_1_CAN_ID = 4;

    Encoder rightEncoder = new Encoder(0, 1);

    public TwikiDrivetrain() {
        super(1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, false);
        getRightMaster().setInverted(true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("right encoder", rightEncoder.getDistance());
    }

    public void followup() {
        getLeftMaster().follow(getRightMaster());
    }

    public void crawlRight() {
        getRightMaster().set(0.15);
    }

    public void crawlLeft() {
        getLeftMaster().set(0.15);
    }

    public void unfollow() {
        CANSparkMax.ExternalFollower f = new CANSparkMax.ExternalFollower(0,0);
        getLeftMaster().follow(f, 0);
    }
}
