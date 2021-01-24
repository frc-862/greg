/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Core extends SubsystemBase {
    private final String name = "CORE";

    Compressor compressor;

    public Core() {
        setName(name);
        compressor = new Compressor(RobotMap.COMPRESSOR_ID);
    }

    @Override
    public void periodic() {
    }

    public void enableComperser(){
        compressor.enabled();
    }

}
