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
import frc.lightning.subsystems.DrivetrainLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.SmartDashDrivetrain;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VelocityTankDrive;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.GregDrivetrain;
import frc.robot.subsystems.NebulaDrivetrain;
import frc.robot.subsystems.TwikiDrivetrain;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private static boolean isNebula() {
    return Files.exists(Paths.get("/home/lvuser/nebula"));
  }

  private static boolean isTwiki() {
    return Files.exists(Paths.get("/home/lvuser/twiki"));
  }

  private static boolean isGreg() {
    return !(isNebula() || isTwiki());
  }

  private static LightningDrivetrain determineDriveTrain() {
    if (isNebula()) {
      System.out.println("Initializing Nebula");
      return NebulaDrivetrain.create();
    }

    if (isTwiki()) {
      System.out.println("Initializing Twiki");
      return new TwikiDrivetrain();
    }

    // default to greg
    System.out.println("Initializing Greg");
    return new GregDrivetrain();
  }
  // The robot's subsystems and commands are defined here...
  // private final Drivetrain drivetrain = new Drivetrain();
  // private final LightningDrivetrain drivetrain = new Drivetrain2Motor();
  private final LightningDrivetrain drivetrain = determineDriveTrain();
  private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
  private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);

  private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
  private final XboxController copilot = new XboxController(JoystickConstants.COPILOT);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    initializeDashboardCommands();

    // set default commands
    if (isTwiki()) {
      drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain,
              () -> -driver.getY(GenericHID.Hand.kLeft),
              () -> driver.getX(GenericHID.Hand.kRight)
      ));
    } else {
      drivetrain.setDefaultCommand(new TankDrive(drivetrain,
              () -> -driver.getY(GenericHID.Hand.kLeft),
              () -> -driver.getY(GenericHID.Hand.kRight)
      ));
    }
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
