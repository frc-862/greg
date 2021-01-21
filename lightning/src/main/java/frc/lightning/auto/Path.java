package frc.lightning.auto;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.lightning.subsystems.LightningDrivetrain;

/**
 * Object class representing a path the robot can follow.
 */
public class Path {

    /**
     * Name of the path
     */
    private String name;

    /**
     * A list of the path's waypoints
     */
    private List<Pose2d> waypoints;

    /**
     * If the robot is moving forward or backward
     */
    private boolean reversed;

    /**
     * Constructor creates path object
     * @param name The name of the path
     * @param waypoints List of waypoints for the optimized path to follow
     */
    public Path(String name, List<Pose2d> waypoints) {
        this(name, waypoints, false);
    }

    /**
     * Constructor creates path object
     * @param name The name of the path
     * @param waypoints List of waypoints for the optimized path to follow
     * @param reversed Direction robot should follow path
     */
    public Path(String name, List<Pose2d> waypoints, boolean reversed) {
        this.name = name;
        this.waypoints = waypoints;
        this.reversed = reversed;
    }

    /**
     * Name of path
     * @return The name of the path
     */
    public String getName() { return name; }

    /**
     * Direction path should be followed
     * @return The direction the path should be followed
     */
    public boolean getReversed() { return reversed; }

    /**
     * Obtains an optimized trajectory the robot should follow so it hits all the waypoints
     * @param drivetrain Drivetrain object of the robot the path should be configured for
     * @return A trajectory the robot can follow
     */
    private Trajectory getTrajectory(LightningDrivetrain drivetrain) { 

        TrajectoryConfig config = new TrajectoryConfig(drivetrain.getConstants().getMaxVelocity(), 
                                                        drivetrain.getConstants().getMaxAcceleration());

        config.setKinematics(drivetrain.getKinematics());
        config = config.setReversed(getReversed());

        Trajectory trajectory;

        try {
            trajectory = TrajectoryGenerator.generateTrajectory(waypoints, config);
        } catch (RuntimeException e) {
            trajectory = TrajectoryGenerator.generateTrajectory(Arrays.asList(new Pose2d(0d, 0d, new Rotation2d()), new Pose2d(1d, 0d, new Rotation2d())), config);
        }

        return trajectory; 

    }

    /**
     * The duration of time it will take the robot to complete the path
     * @param drivetrain Drivetrain object of the robot the path should be configured for
     * @return The number of seconds the robot will need to complete driving the path
     */
    public double getDuration(LightningDrivetrain drivetrain) {
        return this.getTrajectory(drivetrain).getTotalTimeSeconds();
    }

    /**
     * Retrieves the path represented as a command
     * @param drivetrain Drivetrain object of the robot the path should be configured for
     * @return A {@link edu.wpi.first.wpilibj2.command.Command command} representing the path that can be driven by the given drivetrain
     */
    public Command getCommand(LightningDrivetrain drivetrain) {

        Trajectory trajectory = this.getTrajectory(drivetrain);

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