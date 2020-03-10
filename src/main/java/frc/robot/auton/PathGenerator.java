/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auton;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.lightning.subsystems.LightningDrivetrain;

import java.util.Arrays;
import java.util.List;

/**
 * Add your docs here.
 */
public class PathGenerator {

    public enum Paths {

        /* ASSUME SHOOTER FWD, COLLECTOR BACK ON ROBOT */ 

        TEST_PATH(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                new Pose2d(2d, -2d, Rotation2d.fromDegrees(-90d)))),
                                // new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                // new Pose2d(4d, -1d, Rotation2d.fromDegrees(0d)),
                                // new Pose2d(8d, 0d, Rotation2d.fromDegrees(0d)))),
        
        TEST_PATH_TWO(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                    new Pose2d(-2d, -2d, Rotation2d.fromDegrees(90d))), 
                                    true),
        
        INIT_LINE_COLLECT_FWD(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                            new Pose2d(1d, 0d, Rotation2d.fromDegrees(0d)))),
        
        INIT_LINE_FWD2SHOOT(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                            new Pose2d(-1d, 0d, Rotation2d.fromDegrees(0d))),
                                            true),
        
        INIT_LINE_2_TRENCHRUN(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                            new Pose2d(1.75d, 1.5d, Rotation2d.fromDegrees(0d)), // ??
                                            new Pose2d(4.438d, 1.5d, Rotation2d.fromDegrees(0d)))),
        
        INIT_LINE_2_OPP_TRENCHRUN(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                new Pose2d(2.796d, 0d, Rotation2d.fromDegrees(0d)))),

        BACK_TR_2_FRONT_TR(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                            new Pose2d(-1.5, -0.25, Rotation2d.fromDegrees(20d))),
                                            true),

        TWITCH(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                new Pose2d(0d, 0d, Rotation2d.fromDegrees(-10d)), 
                                new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                new Pose2d(0d, 0d, Rotation2d.fromDegrees(10d)),
                                new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)))),

        // OPP_TR_ADJUST(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
        //                             new Pose2d(-0.096d, 0.5d, Rotation2d.fromDegrees(70d)))),// TODO - -70d ???
        
        TRENCHRUN_2_SHOOTING_POSE(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                new Pose2d(-3.01d, -1.5d, Rotation2d.fromDegrees(0d))),
                                                true),
        
        OPP_TRENCHRUN_2_SHOOTING_POSE_INNER(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                            new Pose2d(-1.583d, 2.368d, Rotation2d.fromDegrees(-82.25d)), 
                                                            new Pose2d(-2.166d, 5.068d, Rotation2d.fromDegrees(0d))),
                                                            true),
        
        OPP_TRENCHRUN_2_SHOOTING_POSE_OUTER(Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0)), 
                                                            new Pose2d(-2.213d, 3.141d, Rotation2d.fromDegrees(-26.15d))),
                                                            true);

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

    public double getDuration(LightningDrivetrain drivetrain, Paths path) {
        return path.getTrajectory(drivetrain).getTotalTimeSeconds();
    }

    public RamseteCommand getRamseteCommand(LightningDrivetrain drivetrain, Paths path) {

        Trajectory trajectory = path.getTrajectory(drivetrain);

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

}
