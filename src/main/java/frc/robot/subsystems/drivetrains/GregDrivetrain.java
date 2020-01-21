/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.drivetrains;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

import java.util.function.Consumer;

public class GregDrivetrain extends NeoDrivetrain {

    public GregDrivetrain() {
        super(3, RobotMap.LEFT_1_CAN_ID, RobotMap.RIGHT_1_CAN_ID, Constants.gregTrackWidth);
        initMotorDirections();

        setLeftGains(Constants.leftGains);
        setRightGains(Constants.rightGains);

        REVGains.putGainsToBeTunedOnDash((getName() + "_RIGHT"), Constants.rightGains);
        REVGains.putGainsToBeTunedOnDash((getName() + "_LEFT"), Constants.leftGains);

    }

    public void init() {
        this.resetDistance();
    }

    @Override
    public void periodic() {
        REVGains.updateGainsFromDash((getName() + "_RIGHT"), Constants.rightGains, getRightPIDFC());
        REVGains.updateGainsFromDash((getName() + "_LEFT"), Constants.leftGains, getLeftPIDFC());
    }

    @Override
    public void initMotorDirections() {
        getLeftMaster().setInverted(false);
        getRightMaster().setInverted(true);

        withEachSlaveMotor((s,m) -> s.follow(m));
        withEachSlaveMotorIndexed((m,i) -> m.setInverted(i == 1));
    }

}
