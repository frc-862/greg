package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.InterpolatedMap;
import frc.robot.Constants;

public class ShooterAngle extends SubsystemBase {
    InterpolatedMap shooterAngle = new InterpolatedMap();
    private TalonSRX adjuster;

    public void ShooterAngle(){
        adjuster =new TalonSRX(11);

        //data sheet
        shooterAngle.put(10.0,200.0);
        shooterAngle.put(18.0,300.0);
        shooterAngle.put(35.0,250.0);
        shooterAngle.put(35.0,300.0);

        //motion magic configs
        adjuster.config_kF(0,Constants.kAdjusterF);
        adjuster.config_kD(0,Constants.kAdjusterD);
        adjuster.config_kI(0,Constants.kAdjusterI);
        adjuster.config_kP(0,Constants.kAdjusterP);

        //encoder config
        adjuster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

    }
    @Override
    public void periodic(){

    }

    public double getDesiredAngle(double distance){
        return shooterAngle.get(distance);
    }
    public void setDesiredAngle(double distance) {

    }
    public void setShooterAngle(double angle){
        adjuster.set(ControlMode.MotionMagic,angle);
    }
    public double getAngle() {
        return adjuster.getSelectedSensorPosition();
    }

}
