/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.lightning.subsystems.LightningDrivetrain;

/**
 * Add your docs here.
 */
public class PathGenerator {

    public enum Paths {
        TEST_PATH(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(4d, -1d, new Rotation2d()))),
        BACK_OFF_INIT_LINE(null),
        FWD_OFF_INIT_LINE(null),
        INIT_LINE_2_TRENCHRUN(null),
        INIT_LINE_2_OPP_TRENCHRUN(null),
        TRENCHRUN_2_SHOOTING_POSE(null),
        OPP_TRENCHRUN_2_SHOOTING_POSE(null);

        private List<Pose2d> waypoints;

        private Paths (List<Pose2d> waypoints) {
            this.waypoints = waypoints;
        }

        public Trajectory getTrajectory(LightningDrivetrain drivetrain) { 

            TrajectoryConfig config = new TrajectoryConfig(drivetrain.getConstants().getMaxVelocity(), 
                                                            drivetrain.getConstants().getMaxAcceleration());

            config.setKinematics(drivetrain.getKinematics());
            return TrajectoryGenerator.generateTrajectory(waypoints, config); 

        }

    }
    
    private static SendableChooser<Paths> chooser = new SendableChooser<>();

    public PathGenerator() {
        SmartDashboard.putData("PathChooser", chooser);
        chooser.setDefaultOption(Paths.TEST_PATH.name(), Paths.TEST_PATH);
        for (var path : Paths.values()) chooser.addOption(path.name(), path);
    }

    public RamseteCommand getRamseteCommand(LightningDrivetrain drivetrain) {

        Trajectory trajectory = chooser.getSelected().getTrajectory(drivetrain);

        RamseteCommand cmd = new RamseteCommand(
            trajectory,
            drivetrain::getPose,
            new RamseteController(),
            drivetrain.getFeedforward(),
            drivetrain.getKinematics(),
            drivetrain::getSpeeds,
            drivetrain.getLeftPidController(),
            drivetrain.getRightPidController(),
            drivetrain::setRamseteOutput,
            drivetrain
        );

        return cmd;
        
    }

}
