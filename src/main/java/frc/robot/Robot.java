/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.nio.file.Files;
import java.nio.file.Paths;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.LightningContainer;
import frc.lightning.LightningRobot;
import frc.robot.robots.GregContainer;
import frc.robot.robots.NebulaContainer;
import frc.robot.robots.QuasarContainer;
import frc.robot.robots.TwikiContainer;

public class Robot extends LightningRobot {
    public static final boolean DRIVETRAIN_LOGGING_ENABLED = true;

    private LightningContainer robotContainer;

    @Override
    public void robotInit() {
        robotContainer = getRobot();
        super.robotInit();
        for (var command : robotContainer.getAutonomousCommands()) {
            registerAutonomousCommmand(command);
        }
    }

    private static LightningContainer getRobot() {
        if (isNebula()) {
            System.out.println("Initializing Nebula");
            SmartDashboard.putString("Robot: ", "Nebula");
            return new NebulaContainer();
        }

        if (isTwiki()) {
            System.out.println("Initializing Twiki");            
            SmartDashboard.putString("Robot: ", "Twiki");
            return new TwikiContainer();
        }

        if (isQuasar()) {
            System.out.println("Initializing Quasar");
            SmartDashboard.putString("Robot: ", "Quasar");
            return new QuasarContainer();
        }

        System.out.println("Initializing Greg");
        SmartDashboard.putString("Robot: ", "Greg");
        return new GregContainer();
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
