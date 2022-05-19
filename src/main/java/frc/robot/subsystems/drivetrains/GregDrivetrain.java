package frc.robot.subsystems.drivetrains;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.lightning.LightningConfig;
import frc.lightning.subsystems.IMU.IMUFunction;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.robot.Constants;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;

public class GregDrivetrain extends NeoDrivetrain {

    Encoder leftEncoder; 

    Encoder rightEncoder;

    LightningConfig config;

    public GregDrivetrain(LightningConfig config, Supplier<Rotation2d> heading, IMUFunction zeroHeading) {
        
        super(config, RobotMap.MOTORS_PER_SIDE, RobotMap.LEFT_1_CAN_ID, RobotMap.RIGHT_1_CAN_ID, heading, zeroHeading);
        
        this.config = config;

        leftEncoder = new Encoder(new DigitalInput(RobotMap.LEFT_ENCODER_CHANNEL_A), new DigitalInput(RobotMap.LEFT_ENCODER_CHANNEL_B), false);
        rightEncoder = new Encoder(new DigitalInput(RobotMap.RIGHT_ENCODER_CHANNEL_A), new DigitalInput(RobotMap.RIGHT_ENCODER_CHANNEL_B), true);
        
        initMotorDirections();

        leftEncoder.setDistancePerPulse(RobotConstants.ENCODER_PULSE_TO_METERS);
        rightEncoder.setDistancePerPulse(RobotConstants.ENCODER_PULSE_TO_METERS);

        withEachMotor((m) -> m.burnFlash());

        brake();
        
    }

    @Override
    public void initMotorDirections() {
        getLeftMaster().setInverted(false);
        getRightMaster().setInverted(true);
        withEachSlaveMotor((s,m) -> s.follow(m));
        withEachSlaveMotorIndexed((m,i) -> m.setInverted(i == 1));
    }

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

    @Override
    public void resetDistance() {
        super.resetDistance();
        if((leftEncoder != null) && (rightEncoder != null)) {
            leftEncoder.reset();
            rightEncoder.reset();
        }
    }
    
}
