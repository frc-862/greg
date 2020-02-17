/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.util.RamseteGains;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

public class QuasarDrivetrain extends NeoDrivetrain {

    /**
     * Creates a new QuasarDrivetrain.
     */

    public QuasarDrivetrain() {
        super(RobotMap.MOTORS_PER_SIDE, RobotMap.LEFT_1_CAN_ID, RobotMap.RIGHT_1_CAN_ID, Constants.QUASAR.getTrackWidth(), Constants.QUASAR);
        initMotorDirections();

        setLeftGains(Constants.quasarLeftGains);
        setRightGains(Constants.quasarRightGains);

        REVGains.putGainsToBeTunedOnDash((getName() + "_RIGHT"), Constants.quasarRightGains, getRightPIDFC());
        REVGains.putGainsToBeTunedOnDash((getName() + "_LEFT"), Constants.quasarLeftGains, getLeftPIDFC());


    }

    public void init() {
        this.resetDistance();
        SmartDashboard.putNumber("Test Smartdash Power", 0);
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

    @Override
    public RamseteGains getConstants() {
        return Constants.QUASAR;
    }
}
