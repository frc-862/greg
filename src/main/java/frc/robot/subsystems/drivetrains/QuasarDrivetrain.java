package frc.robot.subsystems.drivetrains;

import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.lightning.LightningConfig;
import frc.lightning.subsystems.CTREDrivetrain;
import frc.lightning.subsystems.IMU.IMUFunction;
import frc.robot.RobotMap;

public class QuasarDrivetrain extends CTREDrivetrain {

    public QuasarDrivetrain(LightningConfig config, Supplier<Rotation2d> heading, IMUFunction zeroHeading) {
        super(config, new TalonFX(RobotMap.LEFT_1_CAN_ID), new TalonFX(RobotMap.RIGHT_1_CAN_ID), new TalonFX[]{new TalonFX(RobotMap.LEFT_2_CAN_ID), new TalonFX(RobotMap.LEFT_3_CAN_ID)},
                new TalonFX[]{new TalonFX(RobotMap.RIGHT_2_CAN_ID), new TalonFX(RobotMap.RIGHT_3_CAN_ID)}, heading, zeroHeading);
        initMotorDirections();
    }

    @Override
    public void initMotorDirections() {
        getLeftMaster().setInverted(true);
        getRightMaster().setInverted(false);
        withEachLeftSlave((m) -> m.setInverted(false));
        withEachRightSlave((m) -> m.setInverted(true));
    }

}
