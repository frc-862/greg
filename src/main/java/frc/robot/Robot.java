/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.lightning.LightningRobot;

public class Robot extends LightningRobot {
    public static final boolean DRIVETRAIN_LOGGING_ENABLED = true;

    private QuasarContainer robotContainer;

    @Override
    public void robotInit() {
        robotContainer = new QuasarContainer();
        super.robotInit();
        for (var command : robotContainer.getAutonomousCommands()) {
            registerAutonomousCommmand(command);
        }
    }
}

