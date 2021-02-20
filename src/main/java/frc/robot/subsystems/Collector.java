/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Collector extends SubsystemBase {

    private VictorSPX linearMotor;
    private VictorSPX longitudinalMotor;
    private DoubleSolenoid pistons;

    public Collector() {

        linearMotor = new WPI_VictorSPX(RobotMap.LINEAR_MOTOR);
        linearMotor.setInverted(true);

        longitudinalMotor = new WPI_VictorSPX(RobotMap.LONGITUDNAL_MOTOR);
        longitudinalMotor.setInverted(true);

        pistons = new DoubleSolenoid(RobotMap.COMPRESSOR_ID, RobotMap.COLLECTOR_IN_CHANNEL, RobotMap.COLLECTOR_OUT_CHANNEL);

    }

    public void collect() { collect(1d); }

    public void eject() { eject(1d); }


    public void collect(double pwr) {
        setPower(pwr);
    }

    public void eject(double pwr) {
        linearMotor.set(ControlMode.PercentOutput, -pwr);
    }

    public void setPower(double pwr) {
        linearMotor.set(ControlMode.PercentOutput, pwr);
        longitudinalMotor.set(ControlMode.PercentOutput, -pwr);
    }

    public void setPowerLongitudinal(double pwr) {
        longitudinalMotor.set(ControlMode.PercentOutput,pwr);
    }

    public double getPowerLinear(){
        return linearMotor.getMotorOutputPercent();
    }

    public double getPowerLongitudinal(){
        return longitudinalMotor.getMotorOutputPercent();
    }

    public void stop() {
        linearMotor.set(ControlMode.PercentOutput, 0d);
        longitudinalMotor.set(ControlMode.PercentOutput, 0d);
    }
    
    public void retract() {
        pistons.set(DoubleSolenoid.Value.kForward);
    }

    public void extend() {
        pistons.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleCollector() {
        if(pistons.get().equals(DoubleSolenoid.Value.kForward)) extend();
        else retract();
    }

    public boolean isOut() {
        return pistons.get().equals(DoubleSolenoid.Value.kReverse);
    }

}
