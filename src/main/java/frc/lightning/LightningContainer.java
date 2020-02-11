/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.auton.PathGenerator;

/**
 * Add your docs here.
 */
public abstract class LightningContainer {
    public abstract void configureButtonBindings();
    public abstract void configureDefaultCommands();
    public abstract void releaseDefaultCommands();

    public PathGenerator getPathGenerator() {
        return null;
    }

    public abstract LightningDrivetrain getDrivetrain();

    // public Command[] getAutonomousCommands() {
    public HashMap<String, Command> getAutonomousCommands() {
        Command[] empty = {};
        return null; // empty;
    }

}
