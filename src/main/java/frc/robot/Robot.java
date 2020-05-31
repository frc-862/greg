/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.LightningContainer;
import frc.lightning.LightningRobot;
import frc.robot.robots.GregContainer;
import frc.robot.robots.NebulaContainer;
import frc.robot.robots.QuasarContainer;
import frc.robot.robots.TwikiContainer;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Robot extends LightningRobot {

    public static final boolean DRIVETRAIN_LOGGING_ENABLED = true;

    public Robot() {
        super(getRobot());
    }

    @Override
    public void robotInit() {
        super.robotInit();

//        Set<String> names = getContainer().getAutonomousCommands().keySet();
//        for(var name : names) {
//            registerAutonomousCommmand(name, getContainer().getAutonomousCommands().get(name));
//            System.out.println("Registered " + name + " command for auton");
//        }
        
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
        // getContainer().getDrivetrain().resetSensorVals();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        // getContainer().getDrivetrain().resetSensorVals();
    }

    private static LightningContainer getRobot() {
        if (isNebula()) {
            System.out.println("Initializing Nebula");
            SmartDashboard.putString("Robot: ", "My Name is Nebula");
            return new NebulaContainer();
        }

        if (isTwiki()) {
            System.out.println("Initializing Twiki");
            SmartDashboard.putString("Robot: ", "My Name is Twiki");
            return new TwikiContainer();
        }

        if (isQuasar()) {
            System.out.println("Initializing Quasar");
            SmartDashboard.putString("Robot: ", "My Name is Quasar");
            return new QuasarContainer();
        }
        else {
            System.out.println("Initializing Greg");
            SmartDashboard.putString("Robot: ", "Hello, My Name is Greg");
            return new GregContainer();
        }
    }

    public static boolean isNebula() {
        return Files.exists(Paths.get("/home/lvuser/nebula"));
    }

    public static boolean isTwiki() {
        return Files.exists(Paths.get("/home/lvuser/twiki"));
    }

    public static boolean isQuasar() {
        return Files.exists(Paths.get("/home/lvuser/quasar"));
    }

    public static boolean isIllusion() {
        return Files.exists(Paths.get("/home/lvuser/illusion"));
    }

    public static boolean isGreg() {
        return !(isNebula() || isTwiki() || isIllusion() || isQuasar());
    }

}
