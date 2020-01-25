/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

public class QuasarDrivetrain extends NeoDrivetrain {

    /**
     * Creates a new QuasarDrivetrain.
     */
    public QuasarDrivetrain() {
        super(RobotMap.MOTORS_PER_SIDE, RobotMap.LEFT_1_CAN_ID, RobotMap.RIGHT_1_CAN_ID, Constants.QUASAR_GAINS.getTrackWidth(), Constants.QUASAR_GAINS);
        initMotorDirections();

        setLeftGains(Constants.quasarLeftGains);
        setRightGains(Constants.quasarRightGains);

        REVGains.putGainsToBeTunedOnDash((getName() + "_RIGHT"), Constants.quasarRightGains);
        REVGains.putGainsToBeTunedOnDash((getName() + "_LEFT"), Constants.quasarLeftGains);

    }

    public void init() {
        this.resetDistance();
    }

    @Override
    public void periodic() {
        super.periodic();
        REVGains.updateGainsFromDash((getName() + "_RIGHT"), Constants.quasarRightGains, getRightPIDFC());
        REVGains.updateGainsFromDash((getName() + "_LEFT"), Constants.quasarLeftGains, getLeftPIDFC());
    }

    @Override
    public void initMotorDirections() {
        getLeftMaster().setInverted(false);
        getRightMaster().setInverted(true);

        withEachLeftSlaveMotorIndexed((m, i) -> {
            if (i == 1) {
                m.follow(getLeftMaster(), true);
            } else {
                m.follow(getLeftMaster(), false);
            }
        });

        withEachRightSlaveMotorIndexed((m, i) -> {
            if (i == 1) {
                m.follow(getRightMaster(), true);
            } else {
                m.follow(getRightMaster(), false);
            }
        });

    }
}
