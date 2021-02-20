/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

    public static final double CLIMB_PWR = 0.75;
    public static final double CLIMB_PWR_DEADZONE = 0.1;

    private CANSparkMax climberLeft; 
    private CANSparkMax climberRight; 

    public Climber() {

        climberLeft = new CANSparkMax(RobotMap.CLIMBER_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberRight = new CANSparkMax(RobotMap.CLIMBER_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless); 

        climberLeft.setIdleMode(IdleMode.kBrake);
        climberRight.setIdleMode(IdleMode.kBrake);

        climberLeft.setInverted(true);
        climberRight.setInverted(false);

        climberLeft.burnFlash();
        climberRight.burnFlash();
        
    }

    public void setPwr(double leftPwr, double rightPwr) {
        if(Math.abs(leftPwr) <= CLIMB_PWR_DEADZONE) leftPwr = 0d;
        if(Math.abs(rightPwr) <= CLIMB_PWR_DEADZONE) rightPwr = 0d; 
        
        climberLeft.set(leftPwr);
        climberRight.set(rightPwr);
    }

    public void setAllPower(double pwr) {
        climberLeft.set(pwr);
        climberRight.set(pwr); 
    }
 
    public void up() {
        climberLeft.set(CLIMB_PWR);
        climberRight.set(CLIMB_PWR);
    }

    public void down() {
        climberLeft.set(-CLIMB_PWR);
        climberRight.set(-CLIMB_PWR);
    }

    public void stop() {
        setAllPower(0d);
    }

    public double getLeft() {
        return climberLeft.get();
    }

    public double getRight() {
        return climberRight.get();
    }

}
