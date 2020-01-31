/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton;

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
        // TEST_PATH(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(-2d, -1d, Rotation2d.fromDegrees(90))), true), // new Pose2d(2d, -2d, Rotation2d.fromDegrees(-90)))),
        // TEST_PATH(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(2d, -1d, Rotation2d.fromDegrees(-90)))),
        TEST_PATH(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(2d, -1d, new Rotation2d()) ,new Pose2d(4d, 0d, new Rotation2d()))),
        TEST_PATH_TWO(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(-4d, 0d, new Rotation2d())), true),
        BACK_OFF_INIT_LINE(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d()))),
        FWD_OFF_INIT_LINE(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d()))),
        INIT_LINE_2_TRENCHRUN(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d()))),
        INIT_LINE_2_OPP_TRENCHRUN(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d()))),
        TRENCHRUN_2_SHOOTING_POSE(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d()))),
        OPP_TRENCHRUN_2_SHOOTING_POSE_INNER(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d()))),
        OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(0d, 0d, new Rotation2d())));

        private List<Pose2d> waypoints;
        private boolean reversed;

        private Paths (List<Pose2d> waypoints) {
            this(waypoints, false);
        }

        private Paths (List<Pose2d> waypoints, boolean reversed) {
            this.waypoints = waypoints;
            this.reversed = reversed;
        }

        private boolean getReversed() { return reversed; }

        public Trajectory getTrajectory(LightningDrivetrain drivetrain) { 

            TrajectoryConfig config = new TrajectoryConfig(drivetrain.getConstants().getMaxVelocity(), 
                                                            drivetrain.getConstants().getMaxAcceleration());

            config.setKinematics(drivetrain.getKinematics());
            config = config.setReversed(getReversed());

            Trajectory trajectory;

            try{
                trajectory = TrajectoryGenerator.generateTrajectory(waypoints, config);
            } catch (RuntimeException e) {
                trajectory = TrajectoryGenerator.generateTrajectory(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(1d, 0d, new Rotation2d())), config);
            }

            return trajectory; 

        }

    }

    public RamseteCommand getRamseteCommand(LightningDrivetrain drivetrain, Paths path) {

        Trajectory trajectory = path.getTrajectory(drivetrain);

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
