/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.InterpolatedMap;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.misc.REVGains;

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
    InterpolatedMap flyWheelSpeed = new InterpolatedMap();
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

        flyWheelSpeed.put(10.0,2500.0);
        flyWheelSpeed.put(18.0,2500.0);
        flyWheelSpeed.put(35.0,3500.0);
        flyWheelSpeed.put(45.0,3500.0);
        motor1 = new CANSparkMax(RobotMap.shooter_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor2 = new CANSparkMax(RobotMap.shooter_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor3 = new CANSparkMax(RobotMap.shooter_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor1encoder = new CANEncoder(motor1);
        motor2encoder = new CANEncoder(motor2);
        motor3encoder = new CANEncoder(motor3);
        motor1PIDFController = motor1.getPIDController();
        motor2PIDFController = motor2.getPIDController();
        motor3PIDFController = motor3.getPIDController();

        setMotor1Gains(Constants.Motor1Gains);
        setMotor2Gains(Constants.Motor2Gains);
        setMotor3Gains(Constants.Motor3Gains);
    }

    public void setPower(double pwr) {
        motor1.set(pwr);
        motor2.set(pwr);
        motor3.set(pwr);
    }

    public void setShooterVelocity(double velocity) {
        this.motor1PIDFController.setReference(velocity, ControlType.kVelocity);
        this.motor2PIDFController.setReference(velocity, ControlType.kVelocity);
        this.motor3PIDFController.setReference(velocity, ControlType.kVelocity);
    }

    public void resetDistance() {
        motor1encoder.setPosition(0.0);
        motor2encoder.setPosition(0.0);
        motor3encoder.setPosition(0.0);
    }

    public void stop() {
        setPower(0d);
    }

    public double getDesiredSpeed(double distance){
        return flyWheelSpeed.get(distance);
    }

    public double getFlywheelMotor1Velocity() {
        return motor1encoder.getVelocity();
    }

    public double getFlywheelMotor2Velocity() {
        return motor2encoder.getVelocity();
    }

    public double getFlywheelMotor3Velocity() {
        return motor3encoder.getVelocity();
    }

    public void aim() {
        // position robot & other based on vision values
    }

}
