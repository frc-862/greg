/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.util.XBoxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VelocityTankDrive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  private final XBoxController driver = new XBoxController(JoystickConstants.DRIVER);
  private final XBoxController copilot = new XBoxController(JoystickConstants.COPILOT);

  public double getRightThrottleInput() { return -driver.getRawAxis(5); }
  public double getLeftThrottleInput() {
    return -driver.getRawAxis(1);
  }

  public double getThrottle() {
    final double stick = copilot.getLeftStickY();
    return stick * stick * Math.signum(stick);
  }

  public double getTurn() {
    final double stick = copilot.getRightStickX();
    return stick * stick * Math.signum(-stick);
  }

  public boolean getQuickTurn() {
    return copilot.aButton.get();
  }

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // set default commands
    drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain,
            () -> driver.getLeftStickY(),
            () -> driver.getRightStickX()
            ));
  }

  private void initializeDashboardCommands() {
    SmartDashboard.putData("OpenLoop", new TankDrive(drivetrain,
            () -> driver.getLeftStickY(),
            () -> driver.getRightStickX()
    ));
    SmartDashboard.putData("ClosedLoop", new VelocityTankDrive(drivetrain,
            () -> driver.getLeftStickY(),
            () -> driver.getRightStickX()
            ));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }

  public Command[] getAutonomousCommands() {
    Command[] result = {  };
    return result;
  }
}
