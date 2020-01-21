/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.robots;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.DrivetrainLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.SmartDashDrivetrain;
import frc.lightning.testing.SystemTest;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.drivetrain.VelocityTankDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.drivetrains.*;
import frc.robot.systemtests.drivetrain.LeftSideMoves;
import frc.robot.systemtests.drivetrain.MoveMasters;
import frc.robot.systemtests.drivetrain.MovePrimarySlaves;
import frc.robot.systemtests.drivetrain.MoveSecondarySlaves;
import frc.robot.systemtests.drivetrain.RightSideMoves;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class QuasarContainer extends LightningContainer{
  private final LightningDrivetrain drivetrain = new QuasarDrivetrain();
  private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
  private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);

  private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
  private final XboxController copilot = new XboxController(JoystickConstants.COPILOT);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public QuasarContainer() {
    // Configure the button bindings
    configureSystemTests();
    configureButtonBindings();
    initializeDashboardCommands();

    drivetrain.setDefaultCommand(new VelocityTankDrive(drivetrain,
            () -> -driver.getY(GenericHID.Hand.kLeft),
            () -> -driver.getY(GenericHID.Hand.kRight)
    ));
    
  }

  private void initializeDashboardCommands() {
    SmartDashboard.putData("OpenLoop", new TankDrive(drivetrain,
            () -> -driver.getY(GenericHID.Hand.kLeft),
            () -> -driver.getY(GenericHID.Hand.kRight)
    ));

    SmartDashboard.putData("ClosedLoop", new VelocityTankDrive(drivetrain,
            () -> -driver.getY(GenericHID.Hand.kLeft),
            () -> -driver.getY(GenericHID.Hand.kRight)
    ));
  }

  private void configureSystemTests() {
    SystemTest.register(new LeftSideMoves(drivetrain));
    SystemTest.register(new RightSideMoves(drivetrain));
    SystemTest.register(new MoveMasters(drivetrain));
    SystemTest.register(new MovePrimarySlaves(drivetrain));
    SystemTest.register(new MoveSecondarySlaves(drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  @Override
  public void configureButtonBindings() {
  }

  @Override
  public Command[] getAutonomousCommands() {
    Command[] result = { getSimplePath() };
    return result;
  }

  private Command getSimplePath() {
    
    final TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(5), Units.feetToMeters(5)); // Max Vel, Max Accel
    config.setKinematics(drivetrain.getKinematics());

    final Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
      Arrays.asList(new Pose2d(), new Pose2d(1d, 0d, new Rotation2d())),
      config
    );

    final RamseteCommand cmd = new RamseteCommand(
      trajectory,
      drivetrain::getPose,
      new RamseteController(),
      drivetrain.getFeedforward(),
      drivetrain.getKinematics(),
      drivetrain::getSpeeds,
      drivetrain.getLeftPidController(),
      drivetrain.getRightPidController(),
      drivetrain::setOutput,
      drivetrain
    );

    return cmd;

  }

}
