/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.*;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.LightningMath;
import frc.lightning.util.REVGains;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
    
    private CANSparkMax motor1;
    private CANSparkMax motor2;
    private CANSparkMax motor3;

    private CANEncoder motor1encoder;
    private CANEncoder motor2encoder;
    private CANEncoder motor3encoder;

    private CANPIDController motor1PIDFController;
    private CANPIDController motor2PIDFController;
    private CANPIDController motor3PIDFController;

    private double setSpeed = 0;
    public double motor1setpoint = 0;
    public double motor2setpoint = 0;
    public double motor3setpoint = 0;

    private static final int PRACTICAL_RPM_MAX = 5000;

    private boolean armed = false;
    double backspin = 1500;

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

    public Shooter() {
        // Init
        //SmartDashboard.putNumber("backspin", backspin);
        NetworkTableInstance.getDefault().getTable("Shooter").getEntry("Backspin").setDouble(backspin);

        CommandScheduler.getInstance().registerSubsystem(this);

        motor1 = new CANSparkMax(RobotMap.SHOOTER_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor2 = new CANSparkMax(RobotMap.SHOOTER_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor3 = new CANSparkMax(RobotMap.SHOOTER_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor1.setIdleMode(CANSparkMax.IdleMode.kCoast); // choose to let coast because their not drive motors
        motor2.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor3.setIdleMode(CANSparkMax.IdleMode.kCoast);
        motor1.setInverted(true);
        motor2.setInverted(true);
        motor3.setInverted(false); // makes motor 3 spin in same direction as motors 1 and 2 
        motor1.setClosedLoopRampRate(.02);
        motor2.setClosedLoopRampRate(.02);
        motor3.setClosedLoopRampRate(.02);
        
        motor1encoder = motor1.getEncoder();
        motor2encoder = motor2.getEncoder();
        motor3encoder = motor3.getEncoder();

        motor1PIDFController = motor1.getPIDController();
        motor2PIDFController = motor2.getPIDController();
        motor3PIDFController = motor3.getPIDController();

        setGains(motor1PIDFController, Constants.Motor1Gains);
        setGains(motor2PIDFController, Constants.Motor2Gains);
        setGains(motor3PIDFController, Constants.Motor3Gains);

        DataLogger.addDataElement("motor 1 speed",()->motor1encoder.getVelocity());
        DataLogger.addDataElement("motor 2 speed",()->motor2encoder.getVelocity());
        DataLogger.addDataElement("motor 3 speed",()->motor3encoder.getVelocity());

        motor1.burnFlash();
        motor2.burnFlash();
        motor3.burnFlash();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("motor 1 speed",motor1encoder.getVelocity());
        SmartDashboard.putNumber("motor 2 speed",motor2encoder.getVelocity());
        SmartDashboard.putNumber("motor 3 speed",motor3encoder.getVelocity());
        backspin = NetworkTableInstance.getDefault().getTable("Shooter").getEntry("Backspin").getDouble(backspin); //SmartDashboard.getNumber("backspin", backspin); // updating backspin from smartdashboard 
        
        // TODO: maybe check all the motors
        if (setSpeed > 400) { 
            //System.out.println("#################\nArmed: " + armed + "\nSetSpeed: " + setSpeed);
            final double speedError = (motor2setpoint - motor2encoder.getVelocity());
            final int BAD_RPM_TOLORANCE = 200;
            final int GOOD_RPM_TOLORANCE = 50;
            if(armed) {
                if (Math.abs(speedError) > BAD_RPM_TOLORANCE) { // if outside of rpm setpoint then not ready to fire
                    armed = false;
                }
            } else {
                if(Math.abs(speedError) < GOOD_RPM_TOLORANCE) { // if within a good range of rpm setpoint then ready to fire
                    armed = true;
                }
            }
        } else { // too slow to arm so not ready to fire
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
        if(setSpeed > 100){ // if motors running 
            motor1setpoint = velocity - backspin;
            motor2setpoint = velocity + backspin;
            motor3setpoint = velocity - backspin;
            this.motor1PIDFController.setReference(LightningMath.constrain(motor1setpoint, 0, PRACTICAL_RPM_MAX), ControlType.kVelocity);
            this.motor2PIDFController.setReference(LightningMath.constrain(motor2setpoint, 0, PRACTICAL_RPM_MAX), ControlType.kVelocity);
            this.motor3PIDFController.setReference(LightningMath.constrain(motor3setpoint, 0, PRACTICAL_RPM_MAX), ControlType.kVelocity);
        } else { // setting motors equal to zero
            motor1setpoint = 0;
            motor2setpoint = 0;
            motor3setpoint = 0;
            this.motor1PIDFController.setReference(0, ControlType.kVoltage);
            this.motor2PIDFController.setReference(0, ControlType.kVoltage);
            this.motor3PIDFController.setReference(0, ControlType.kVoltage);
        }
    }

    public void resetDistance() {
        motor1encoder.setPosition(0.0);
        motor2encoder.setPosition(0.0);
        motor3encoder.setPosition(0.0);
    }

    public void stop() {
        setShooterVelocity(0);
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

}
