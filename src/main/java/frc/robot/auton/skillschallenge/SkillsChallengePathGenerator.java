/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton.skillschallenge;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.auto.Path;
import frc.lightning.auto.Paths;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.DoubleSupplier;

/**
 * Add your docs here.
 */

public class SkillsChallengePathGenerator {

    static{
        Paths.register("IR Blue A", new Path("Blue A", "paths/output/BlueA.wpilib.json", false));
        Paths.register("IR Blue B", new Path("Blue B","paths/output/BlueB.wpilib.json", false));
        Paths.register("IR Red A", new Path("Red A","paths/output/RedA.wpilib.json", false));
        Paths.register("IR Red B", new Path("Red B","paths/output/RedB.wpilib.json", false));
        Paths.register("IR Barrel Racing", new Path("Barrel Racing","paths/output/BarrelRacing.wpilib.json", false));
        Paths.register("IR Slalom", new Path("Slalom","paths/output/Slalom.wpilib.json", false));
        Paths.register("IR Bounce 1", new Path("Bounce 1","paths/output/Bounce1.wpilib.json", false));
        Paths.register("IR Bounce 2", new Path("Bounce 2","paths/output/Bounce2.wpilib.json", false));
        Paths.register("IR Bounce 3", new Path("Bounce 3","paths/output/Bounce3.wpilib.json", false));
        Paths.register("IR Bounce 4", new Path("Bounce 4","paths/output/Bounce4.wpilib.json", false));
    }
/*
    private static Trajectory Path(String jsonPath){
        
        Trajectory trajectory = null;
        try {
            java.nio.file.Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(jsonPath);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + jsonPath, ex.getStackTrace());
        }   

        return trajectory;
    }

    public double getDuration(LightningDrivetrain drivetrain, Path path) {
        return map.get(path).getTotalTimeSeconds();
    }

    static public RamseteCommand generateRamseteCommand(LightningDrivetrain drivetrain, Path path) {
        Trajectory trajectory = map.get(path);

        RamseteCommand cmd = new RamseteCommand(
                trajectory,
                drivetrain::getRelativePose,
                new RamseteController(),
                drivetrain.getFeedforward(),
                drivetrain.getKinematics(),
                drivetrain::getSpeeds,
                drivetrain.getLeftPidController(),
                drivetrain.getRightPidController(),
                drivetrain::setRamseteOutput,
                drivetrain
        ) {
            @Override
            public void initialize() {
                super.initialize();
                drivetrain.setRelativePose();
            }
            @Override
            public void end(boolean interrupted) {
                super.end(interrupted);
                drivetrain.stop();
            }
        };

        return cmd;

    }

    public RamseteCommand getRamseteCommand(LightningDrivetrain drivetrain, Path path) {
        return SkillsChallengePathGenerator.generateRamseteCommand(drivetrain, path);
    }*/

}
