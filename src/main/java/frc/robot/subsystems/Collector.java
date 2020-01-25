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
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {

    //Components
    private VictorSPX linearMotor;
    private VictorSPX longitudinalMotor;

    /**
     * Creates a new Collector.
     */
    public Collector() {
        // Init
        linearMotor = new WPI_VictorSPX(11);
        longitudinalMotor = new WPI_VictorSPX(12);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void deployGround() {

    }

    public void deployPort() {

    }

    public void retract() {

    }

    public void collect() {
        collect(1d);
    }

    public void eject() {
        eject(-1d);
    }


    public void collect(double pwr) {
        setPower(pwr);
    }

    public void eject(double pwr) {
        linearMotor.set(ControlMode.PercentOutput,pwr);
    }

    public void setPower(double pwr) {
        linearMotor.set(ControlMode.PercentOutput,pwr);
        longitudinalMotor.set(ControlMode.PercentOutput,-pwr);
    }

    public void setPowerLongitudinal(double pwr) {
        longitudinalMotor.set(ControlMode.PercentOutput,pwr);
    }

    public void stop() {
        linearMotor.set(ControlMode.PercentOutput,0);
        longitudinalMotor.set(ControlMode.PercentOutput,0);
    }

}
