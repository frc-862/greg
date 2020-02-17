package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.InterpolatedMap;
import frc.robot.Constants;

public class ShooterAngle extends SubsystemBase {
    InterpolatedMap shooterAngle = new InterpolatedMap();
    private TalonSRX adjuster;

    public ShooterAngle (){
        adjuster = new TalonSRX(11);
        System.out.println("Adjuster created: " + adjuster);

        //data sheet
        shooterAngle.put(10.0,400.0);
        shooterAngle.put(18.0,300.0);
        shooterAngle.put(35.0,250.0);
        shooterAngle.put(45.0,200.0);
        //left is in encoder ticks

        //motion magic configs
        adjuster.config_kF(0,Constants.kAdjusterF);
        adjuster.config_kD(0,Constants.kAdjusterD);
        adjuster.config_kI(0,Constants.kAdjusterI);
        adjuster.config_kP(0,Constants.kAdjusterP);

        //encoder config
        adjuster.configSelectedFeedbackSensor(FeedbackDevice.Analog,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

        CommandScheduler.getInstance().registerSubsystem(this);

    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Shooter angle",getAngle());
    }

    public double getDesiredAngle(double distance){
        return shooterAngle.get(distance);
    }
    public void setDesiredAngle(double distance) {
        adjuster.set(ControlMode.MotionMagic,shooterAngle.get(distance));
    }

    public void setShooterAngle(double angle){
        adjuster.set(ControlMode.MotionMagic,angle);
    }

    public double getAngle() {
        System.out.println("Adjuster: " + adjuster);
        return adjuster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    }

}
