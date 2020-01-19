/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {

    //Components

    /**
     * Creates a new Collector.
     */
    public Collector() {
        // Init
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

    }

    public void eject(double pwr) {

    }

    public void setPower(double pwr) {
        collect(pwr);
    }

    public void stop() {
        setPower(0d);
    }

}
