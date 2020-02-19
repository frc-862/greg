/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import edu.wpi.first.wpilibj.Encoder;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.util.RamseteGains;
import frc.robot.Constants;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

public class GregDrivetrain extends NeoDrivetrain {
    Encoder leftEncoder = new Encoder(0,1);
    Encoder rightEncoder = new Encoder(2,3);

    // needs to return meters
    @Override
    public double getLeftDistance() {
        return leftEncoder.getDistance();
    }

    // needs to return meters
    @Override
    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    // needs to return meters/sec
    @Override
    public double getLeftVelocity() {
        return leftEncoder.getRate();
    }

    // needs to return meters/sec
    @Override
    public double getRightVelocity() {
        return rightEncoder.getRate();
    }

    public GregDrivetrain() {
        super(3, RobotMap.LEFT_1_CAN_ID, RobotMap.RIGHT_1_CAN_ID, Constants.GREG.getTrackWidth(), Constants.GREG);
        initMotorDirections();

        leftEncoder.setDistancePerPulse(RobotConstants.ENCODER_PULSE_TO_METERS);
        rightEncoder.setDistancePerPulse(RobotConstants.ENCODER_PULSE_TO_METERS);

        setLeftGains(Constants.leftGains);
        setRightGains(Constants.rightGains);

        withEachMotor((m) -> m.burnFlash());

        REVGains.putGainsToBeTunedOnDash((getName() + "_RIGHT"), Constants.rightGains, getRightPIDFC());
        REVGains.putGainsToBeTunedOnDash((getName() + "_LEFT"), Constants.leftGains, getLeftPIDFC());
    }

    public void init() {
        this.resetDistance();
    }

    @Override
    public void initMotorDirections() {
        getLeftMaster().setInverted(false);
        getRightMaster().setInverted(true);

        withEachSlaveMotor((s,m) -> s.follow(m));
        withEachSlaveMotorIndexed((m,i) -> m.setInverted(i == 1));

    }

    @Override
    public RamseteGains getConstants() {
        return Constants.GREG;
    }

}
