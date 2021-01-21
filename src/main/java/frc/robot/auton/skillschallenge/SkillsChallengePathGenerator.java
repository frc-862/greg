/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton.skillschallenge;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.DriverStation;

import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.DoubleSupplier;

/**
 * Add your docs here.
 */
public class SkillsChallengePathGenerator {

    public enum Path{
        FIELD_A_BLUE,
        BARREL_RACING,
        FIELD_A_RED,
        FIELD_B_RED,
        FIELD_B_BLUE,
        SLALOM,
        BOUNCE1,
        BOUNCE2,
        BOUNCE3,
        BOUNCE4
    }
    private static HashMap<Path, Trajectory> map = new HashMap<>();

    static{
        map.put(Path.FIELD_A_BLUE, getTrajectoryFromJSON("paths/output/BlueA.wpilib.json"));
        map.put(Path.FIELD_B_BLUE, getTrajectoryFromJSON("paths/output/BlueB.wpilib.json"));
        map.put(Path.FIELD_A_RED, getTrajectoryFromJSON("paths/output/RedA.wpilib.json"));
        map.put(Path.FIELD_B_RED, getTrajectoryFromJSON("paths/output/RedB.wpilib.json"));
        map.put(Path.BARREL_RACING, getTrajectoryFromJSON("paths/output/BarrelRacing.wpilib.json"));
        map.put(Path.SLALOM, getTrajectoryFromJSON("paths/output/Slalom.wpilib.json"));
        map.put(Path.BOUNCE1, getTrajectoryFromJSON("paths/output/Bounce1.wpilib.json"));
        map.put(Path.BOUNCE2, getTrajectoryFromJSON("paths/output/Bounce2.wpilib.json"));
        map.put(Path.BOUNCE3, getTrajectoryFromJSON("paths/output/Bounce3.wpilib.json"));
        map.put(Path.BOUNCE4, getTrajectoryFromJSON("paths/output/Bounce4.wpilib.json"));
    }

    private static Trajectory getTrajectoryFromJSON(String jsonPath){
        
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
    }

}
