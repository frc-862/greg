/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.nio.file.Files;
import java.nio.file.Paths;

import frc.lightning.LightningRobot;
import frc.robot.subsystems.drivetrains.GregDrivetrain;
import frc.robot.subsystems.drivetrains.NebulaDrivetrain;
import frc.robot.subsystems.drivetrains.TwikiDrivetrain;

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

    private static LightningContainer getRobot() {
        if (isNebula()) {
            System.out.println("Initializing Nebula");
            return NebulaDrivetrain.create();
        }

        if (isTwiki()) {
            System.out.println("Initializing Twiki");
            return new TwikiDrivetrain();
        }

        if (isQuasar()) {
            System.out.println("Initializing Twiki");
            return new TwikiDrivetrain();
        }

        // default to greg
        System.out.println("Initializing Greg");
        return new GregDrivetrain();
    }

    private static boolean isNebula() {
        return Files.exists(Paths.get("/home/lvuser/nebula"));
    }

    private static boolean isTwiki() {
        return Files.exists(Paths.get("/home/lvuser/twiki"));
    }

    private static boolean isQuasar() {
        return Files.exists(Paths.get("/home/lvuser/quasar"));
    }

    private static boolean isGreg() {
        return !(isNebula() || isTwiki());
    }

}
