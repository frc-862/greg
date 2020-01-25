/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.robots;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.DrivetrainLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.SmartDashDrivetrain;
import frc.lightning.testing.SystemTest;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IMU;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.drivetrains.TwikiDrivetrain;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.systemtests.drivetrain.LeftSideMoves;
import frc.robot.systemtests.drivetrain.RightSideMoves;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class TwikiContainer extends LightningContainer {
    private final TwikiDrivetrain drivetrain = new TwikiDrivetrain();
    private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
    private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);
//    private final IMU IMU = new IMU();
    // private final LED led = new LED();

  private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
  private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public TwikiContainer() {
        configureButtonBindings();
        configureDefaultCommands();
        initializeDashboardCommands();
        configureSystemTests();
    }

    private void configureSystemTests() {
        SystemTest.register(new LeftSideMoves(drivetrain));
        SystemTest.register(new RightSideMoves(drivetrain));
    }

    public void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain,
                                     () -> -driver.getY(GenericHID.Hand.kLeft),
                                     () -> driver.getX(GenericHID.Hand.kRight)
                                                    ));
    }

    public void releaseDefaultCommands() {
        // drivetrain.setDefaultCommand(new RunCommand(() -> {}, drivetrain));
    }

    private void initializeDashboardCommands() {
        SmartDashboard.putData("Followup", new InstantCommand(() -> drivetrain.followup(), drivetrain));
        SmartDashboard.putData("Left Crawl", new RunCommand(() -> drivetrain.crawlLeft(), drivetrain));
        SmartDashboard.putData("Right Crawl", new RunCommand(() -> drivetrain.crawlRight(), drivetrain));
        SmartDashboard.putData("Stop", new InstantCommand(() -> drivetrain.stop(), drivetrain));
        SmartDashboard.putData("Unfollow", new InstantCommand(() -> drivetrain.unfollow(), drivetrain));
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
    Command[] result = {  };
    return result;
  }

  @Override
  public LightningDrivetrain getDrivetrain() { return drivetrain; }

}

