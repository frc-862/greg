/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.util.RamseteGains;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;
import frc.lightning.subsystems.LightningDrivetrain;

public class EddieDrivetrain implements LightningDrivetrain {

    /**
     * Creates a new QuasarDrivetrain.
     */
    public EddieDrivetrain() {
        // super(RobotMap.MOTORS_PER_SIDE, RobotMap.LEFT_1_CAN_ID,
        // RobotMap.RIGHT_1_CAN_ID, Constants.QUASAR.getTrackWidth(), Constants.QUASAR);
        // initMotorDirections();
        // setLeftGains(Constants.quasarLeftGains);
        // setRightGains(Constants.quasarRightGains);

        // REVGains.putGainsToBeTunedOnDash((getName() + "_RIGHT"),
        // Constants.quasarRightGains);
        // REVGains.putGainsToBeTunedOnDash((getName() + "_LEFT"),
        // Constants.quasarLeftGains);
        // super(3, RobotMap.LEFT_1_CAN_ID, RobotMap.RIGHT_1_CAN_ID,
        // Constants.GREG.getTrackWidth(), Constants.GREG);
        // initMotorDirections();

        // setLeftGains(Constants.leftGains);
        // setRightGains(Constants.rightGains);

        // REVGains.putGainsToBeTunedOnDash((getName() + "_RIGHT"),
        // Constants.rightGains);
        // REVGains.putGainsToBeTunedOnDash((getName() + "_LEFT"), Constants.leftGains);

    }

    public void init() {
        // this.resetDistance();
    }

    @Override
    public void setPower(double left, double right) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVelocity(double left, double right) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetDistance() {
        // TODO Auto-generated method stub

    }

    @Override
    public double getLeftDistance() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getRightDistance() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getLeftVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getRightVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setOutput(double leftVolts, double rightVolts) {
        // TODO Auto-generated method stub

    }

    @Override
    public void brake() {
        // TODO Auto-generated method stub

    }

    @Override
    public void coast() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initMotorDirections() {
        // TODO Auto-generated method stub

    }

    @Override
    public RamseteGains getConstants() {
        // TODO Auto-generated method stub
        return null;
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

   // @Override
   // public void periodic() {
   //     REVGains.updateGainsFromDash((getName() + "_RIGHT"), Constants.rightGains, getRightPIDFC());
   //     REVGains.updateGainsFromDash((getName() + "_LEFT"), Constants.leftGains, getLeftPIDFC());
   // }

    //@Override
    //public void initMotorDirections() {
        //getLeftMaster().setInverted(false);
        //getRightMaster().setInverted(true);

        //withEachSlaveMotor((s,m) -> s.follow(m));
        
   // }
    //@Override
   //public RamseteGains getConstants() {
        //return Constants.GREG;
    //}
    

   
}
