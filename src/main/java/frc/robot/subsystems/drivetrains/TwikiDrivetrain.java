/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.util.RamseteGains;
import frc.robot.Constants;

public class TwikiDrivetrain extends NeoDrivetrain {
    // DRIVETRAIN
    public static final int LEFT_1_CAN_ID = 4;
    public static final int RIGHT_1_CAN_ID = 1;

    public TwikiDrivetrain() {
        super(1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, 2d, Constants.TWIKI);
        initMotorDirections();
//        getRightMaster().setInverted(true);


        setLeftGains(Constants.leftGains);
        setRightGains(Constants.rightGains);
    }

    @Override
    public void periodic() {
    }

    @Override
    public void initMotorDirections() {
        getRightMaster().setInverted(true);
        getLeftMaster().setInverted(false);
    }


    public void followup() {
    }

    public void crawlRight() {
        getRightMaster().set(0.15);
    }

    public void crawlLeft() {
        getLeftMaster().set(0.15);
    }

    public void resetDistance() {
        // leftEncoder.setPosition(0.0);
        // rightEncoder.setPosition(0.0);
    }

    public double getLeftDistance() {
        return 0d; // leftEncoder.getPosition();
    }

    public double getRightDistance() {
        return 0d; // rightEncoder.getPosition();
    }

    public double getLeftVelocity() {
        return 0d; // leftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return 0d; // rightEncoder.getVelocity();
    }

//    public CANSparkMax getRightMaster() {
//        return getRightMaster();
//    }

//    public CANSparkMax getLeftMaster() {
//        return getLeftMaster();
//    }

    private DifferentialDrive differentialDrive = null;

    public DifferentialDrive getDifferentialDrive() {
        if (differentialDrive == null) {
            differentialDrive = new DifferentialDrive(getLeftMaster(), getRightMaster());
            differentialDrive.setRightSideInverted(false);
        }
        return differentialDrive;
    }

    @Override
    public void brake() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kBrake));
    }

    @Override
    public void coast() {
        this.withEachMotor(m -> m.setIdleMode(IdleMode.kCoast));
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
