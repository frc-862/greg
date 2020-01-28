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
import frc.robot.auton.*;
import frc.robot.auton.PathGenerator.Paths;
import frc.robot.Robot;
import frc.robot.commands.drivetrain.VoltDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
public class QuasarContainer extends LightningContainer {
    private final LightningDrivetrain drivetrain = new QuasarDrivetrain();
    private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
    private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);

  private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
  private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);

  private final PathGenerator pathGenerator;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public QuasarContainer() {
    // Configure the button bindings
    configureSystemTests();
    configureButtonBindings();
    initializeDashboardCommands();

    pathGenerator = new PathGenerator();

    drivetrain.setDefaultCommand(new VoltDrive(drivetrain,
            () -> -driver.getY(GenericHID.Hand.kLeft),
            () -> -driver.getY(GenericHID.Hand.kRight)
    ));
    
  }

  private void initializeDashboardCommands() {}

  private void configureSystemTests() {
    SystemTest.register(new LeftSideMoves(drivetrain));
    SystemTest.register(new RightSideMoves(drivetrain));
    SystemTest.register(new MoveMasters(drivetrain));
    SystemTest.register(new MovePrimarySlaves(drivetrain));
    SystemTest.register(new MoveSecondarySlaves(drivetrain));
  }

  @Override
  public Command[] getAutonomousCommands() {
    Command[] result = { getPathCMD() };
    return result;
  }

  private Command getPathCMD() {
    return (new SequentialCommandGroup(
      pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH),
      new InstantCommand(drivetrain::resetSensorVals, drivetrain),
      new WaitCommand(1d),
      pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO)
    ));
    // return pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH);
  }

  @Override
  public PathGenerator getPathGenerator() {
    return pathGenerator;
  }

  @Override
  public void configureDefaultCommands() {
    // TODO Auto-generated method stub

  }

  @Override
  public void releaseDefaultCommands() {
    // TODO Auto-generated method stub

  }

  @Override
  public LightningDrivetrain getDrivetrain() { return drivetrain; }

  @Override
  public void configureButtonBindings() {}

}
