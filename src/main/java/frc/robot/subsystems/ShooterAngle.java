package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.InterpolatedMap;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;

public class ShooterAngle extends SubsystemBase {
    public static final int REVERSE_SENSOR_LIMIT = 75;
    public static final int FORWARD_SENSOR_LIMIT = 155;

    InterpolatedMap shooterAngle = new InterpolatedMap();
    private double setPoint = 100;
    private double Kp = .2;


    private TalonSRX adjuster;

    public ShooterAngle () {
        adjuster = new TalonSRX(15);
        System.out.println("Adjuster created: " + adjuster);

        shooterAngleConfig();


        //left is in encoder ticks

        adjuster.configForwardSoftLimitEnable(true);
        adjuster.configReverseSoftLimitEnable(true);
        adjuster.configReverseSoftLimitThreshold(REVERSE_SENSOR_LIMIT);
        adjuster.configForwardSoftLimitThreshold(FORWARD_SENSOR_LIMIT);

        //motion magic configs
        adjuster.config_kF(0,Constants.kAdjusterF);
        adjuster.config_kD(0,Constants.kAdjusterD);
        adjuster.config_kI(0,Constants.kAdjusterI);
        adjuster.config_kP(0,Constants.kAdjusterP);

        adjuster.configMotionCruiseVelocity(8, 0);
        adjuster.configMotionAcceleration(8, 0);

        //encoder config
        adjuster.configSelectedFeedbackSensor(FeedbackDevice.Analog,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

        CommandScheduler.getInstance().registerSubsystem(this);

    }
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Shooter angle",getAngle());
        adjusterControlLoop();
    }

    private void adjusterControlLoop() {
        if (!(LightningMath.epsilonEqual(setPoint,getAngle(),2))) {
            if(setPoint-getAngle() < 0) {
                setPower(LightningMath.constrain((setPoint-getAngle())*Kp,-1,1));
            }else {
                setPower(LightningMath.constrain((setPoint-getAngle())*Kp,-1,1));
            }

        } else {
            setPower(0);
        }
    }

    public double getDesiredAngle(double distance){
        return shooterAngle.get(distance);
    }
    public void setDesiredAngle(double distance) {
        setPoint=distance;
    }
    public void setPower(double pwr){
        adjuster.set(ControlMode.PercentOutput,pwr);
    }

    public void setShooterAngle(double angle){
        setPoint=angle;
    }

    public double getAngle() {
//        System.out.println("Adjuster: " + adjuster);
        return adjuster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    }
    private void shooterAngleConfig(){
        //data sheet
        shooterAngle.put(10.0, 400.0);
        shooterAngle.put(18.0, 300.0);
        shooterAngle.put(35.0, 250.0);
        shooterAngle.put(45.0, 200.0);
    }

}
