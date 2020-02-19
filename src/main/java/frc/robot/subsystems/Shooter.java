/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.InterpolatedMap;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

import java.util.function.IntConsumer;

public class Shooter extends SubsystemBase {
    // Components
    private CANSparkMax motor1;
    private CANSparkMax motor2;
    private CANSparkMax motor3;
    private CANEncoder motor1encoder;
    private CANEncoder motor2encoder;
    private CANEncoder motor3encoder;
    private CANPIDController motor1PIDFController;
    private CANPIDController motor2PIDFController;
    private CANPIDController motor3PIDFController;
    private int ballsFired;
    private double setSpeed = 0;
    InterpolatedMap flyWheelSpeed = new InterpolatedMap();
    private boolean armed = false;
    private IntConsumer whenBallShot;
    /**
     * Creates a new Shooter.
     */
    private static void setGains(CANPIDController controller, REVGains gains) {
        controller.setP(gains.getkP());
        controller.setI(gains.getkI());
        controller.setD(gains.getkD());
        controller.setFF(gains.getkFF());
        controller.setIZone(gains.getkIz());
        controller.setOutputRange(gains.getkMinOutput(), gains.getkMaxOutput());
    }

    public void setMotorGains(REVGains gains) {
        setMotor1Gains(gains);
        setMotor2Gains(gains);
        setMotor3Gains(gains);
    }

    public void setMotor1Gains(REVGains gains) {
        setGains(motor1PIDFController, gains);
    }

    public void setMotor2Gains(REVGains gains) {
        setGains(motor2PIDFController, gains);
    }

    public void setMotor3Gains(REVGains gains) {
        setGains(motor3PIDFController, gains);
    }

    public Shooter() {
        // Init
        CommandScheduler.getInstance().registerSubsystem(this);

        configShooterSpeed();
        motor1 = new CANSparkMax(RobotMap.shooter_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor2 = new CANSparkMax(RobotMap.shooter_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor3 = new CANSparkMax(RobotMap.shooter_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor2.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor3.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor1.setInverted(true);
        motor2.setInverted(true);
        motor3.setInverted(false);
        motor1.setClosedLoopRampRate(.02);
        motor2.setClosedLoopRampRate(.02);
        motor3.setClosedLoopRampRate(.02);
        motor1encoder = new CANEncoder(motor1);
        motor2encoder = new CANEncoder(motor2);
        motor3encoder = new CANEncoder(motor3);
        motor1PIDFController = motor1.getPIDController();
        motor2PIDFController = motor2.getPIDController();
        motor3PIDFController = motor3.getPIDController();

        setMotor1Gains(Constants.Motor1Gains);
        setMotor2Gains(Constants.Motor2Gains);
        setMotor3Gains(Constants.Motor3Gains);
        DataLogger.addDataElement("motor 1 speed",()->motor1encoder.getVelocity());
        DataLogger.addDataElement("motor 2 speed",()->motor2encoder.getVelocity());
        DataLogger.addDataElement("motor 3 speed",()->motor3encoder.getVelocity());
    }

    private void configShooterSpeed() {
        flyWheelSpeed.put(10.0,2500.0);
        flyWheelSpeed.put(18.0,2500.0);
        flyWheelSpeed.put(35.0,3500.0);
        flyWheelSpeed.put(45.0,3500.0);
    }

    public void setWhenBallShot(IntConsumer whenBallShot) {
        this.whenBallShot = whenBallShot;
    }

    public void shotBall() { ballsFired--; }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("motor 1 speed",motor1encoder.getVelocity());
        SmartDashboard.putNumber("motor 2 speed",motor2encoder.getVelocity());
        SmartDashboard.putNumber("motor 3 speed",motor3encoder.getVelocity());

        if(setSpeed > 400){
            final double speedError = (setSpeed - motor2encoder.getVelocity());
            if(armed) {
                if (speedError > 200) {
                    ballsFired++;
                    if(whenBallShot != null) {
                        whenBallShot.accept(ballsFired);
                    }
                    armed = false;
                }
            } else {
                if(speedError < 60) {
                    armed = true;
                }
            }
        } else {
            armed = false;
        }

    }

    public void setPower(double pwr) {
        motor1.set(pwr);
        motor2.set(pwr);
        motor3.set(pwr);
    }

    public void setShooterVelocity(double velocity) {
        setSpeed = velocity;
        this.motor1PIDFController.setReference(LightningMath.constrain(velocity-1500,0,5000), ControlType.kVelocity);
        this.motor2PIDFController.setReference(LightningMath.constrain(velocity+1500,0,5000), ControlType.kVelocity);
        this.motor3PIDFController.setReference(LightningMath.constrain(velocity-1500,0,5000), ControlType.kVelocity);
    }

    public void resetDistance() {
        motor1encoder.setPosition(0.0);
        motor2encoder.setPosition(0.0);
        motor3encoder.setPosition(0.0);
    }

    public void stop() {
        setPower(0d);
    }

    public double getDesiredSpeed(double distance) {
        return flyWheelSpeed.get(distance);
    }

    public double getFlywheelMotor1Velocity() {
        return motor1encoder.getVelocity();
    }

    public double getFlywheelMotor2Velocity() {
        return motor2encoder.getVelocity();
    }

    public int getBallsFired(){

        return ballsFired;
    }

    public void reeeeesetBallsFired(){
        ballsFired = 0;
    }


    public double getFlywheelMotor3Velocity() {
        return motor3encoder.getVelocity();
    }

    public void aim() {
        // position robot & other based on vision values
    }

}
