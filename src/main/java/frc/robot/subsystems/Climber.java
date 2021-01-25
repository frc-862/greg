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

    // Components

    private CANSparkMax climberLeft; 
    private CANSparkMax climberRight; 

    /**
     * Creates a new Climber.
     */
    public Climber() {
        climberLeft = new CANSparkMax(RobotMap.CLIMBER_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        climberRight = new CANSparkMax(RobotMap.CLIMBER_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless); 

        climberLeft.setIdleMode(IdleMode.kBrake);
        climberRight.setIdleMode(IdleMode.kBrake);

        climberLeft.setInverted(true);
        climberRight.setInverted(false);
        //Init

        climberLeft.burnFlash();
        climberRight.burnFlash();
    }

    public void setPwr(double leftPwr, double rightPwr) {
        if(Math.abs(leftPwr) <= 0.1) {
            leftPwr = 0d; 
        }
        if(Math.abs(rightPwr) <= 0.1) {
            rightPwr = 0d; 
        }
        climberLeft.set(leftPwr);
        climberRight.set(rightPwr);
    }

    public void setAllPower(double pwr) {
        climberLeft.set(pwr);
        climberRight.set(pwr); 
    }
 
    public void up() {
        climberLeft.set(0.75);
        climberRight.set(0.75);
    }

    public void down() {
        climberLeft.set(-0.75);
        climberRight.set(-0.75);
    }

    public void stop() {
        setAllPower(0d);
    }

}
