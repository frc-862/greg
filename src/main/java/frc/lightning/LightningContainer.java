/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lightning.subsystems.LightningDrivetrain;

/**
 * Add your docs here.
 */
public abstract class LightningContainer {
    public abstract void configureButtonBindings();
    public abstract void configureDefaultCommands();
    public abstract void releaseDefaultCommands();

    public abstract LightningDrivetrain getDrivetrain();

    public Command[] getAutonomousCommands() {
        Command[] empty = {};
        return empty;
    }
    
}
