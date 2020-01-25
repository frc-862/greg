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
    public static final int LEFT_1_CAN_ID = 1;
    public static final int RIGHT_1_CAN_ID = 4;

    Encoder rightEncoder = new Encoder(0, 1);

    public TwikiDrivetrain() {
        super(1, LEFT_1_CAN_ID, RIGHT_1_CAN_ID, 0d, null);
        getRightMaster().setInverted(true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("right encoder", rightEncoder.getDistance());
    }

    @Override
    public void initMotorDirections() {
        getRightMaster().setInverted(true);
        getLeftMaster().setInverted(false);
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

    public CANSparkMax getRightMaster() {
        return getRightMaster();
    }

    public CANSparkMax getLeftMaster() {
        return getLeftMaster();
    }

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
    public void setOutput(double leftVolts, double rightVolts) {
        // TODO Auto-generated method stub

    }

    @Override
    public DifferentialDriveKinematics getKinematics() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pose2d getPose() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DifferentialDriveWheelSpeeds getSpeeds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PIDController getLeftPidController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PIDController getRightPidController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getRightVolts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getLeftVolts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setRamseteOutput(double leftVolts, double rightVolts) {
        // TODO Auto-generated method stub

    }

    @Override
    public RamseteGains getConstants() {
        // TODO Auto-generated method stub
        return null;
    }
}
