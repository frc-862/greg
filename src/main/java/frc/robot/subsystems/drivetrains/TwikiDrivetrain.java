/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.util.RamseteGains;
import frc.robot.Constants;

public class TwikiDrivetrain extends NeoDrivetrain {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID = 4;
    public static final int RIGHT_1_CAN_ID = 1;

    public TwikiDrivetrain() {
        super(1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, 0.558371759, Constants.TWIKI);
        initMotorDirections();

        setLeftGains(Constants.leftTwikiGains);
        setRightGains(Constants.rightTwikiGains);
    }

    @Override
    public void periodic() {
        super.periodic();
        SmartDashboard.putString("Pose", getPose().toString());
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

    public CANSparkMax[] getLeftMotors() {
        CANSparkMax[] motors = new CANSparkMax[1];
        motors[0] = getLeftMaster();
        return motors;
    }

    public CANSparkMax[] getRightMotors() {
        CANSparkMax[] motors = new CANSparkMax[1];
        motors[0] = getRightMaster();
        return motors;
    }

    @Override
    public RamseteGains getConstants() {
        // TODO Auto-generated method stub
        return Constants.TWIKI;
    }
}
