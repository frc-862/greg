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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.DrivetrainLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.SmartDashDrivetrain;
import frc.lightning.testing.SystemTest;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.auton.AutonGenerator;
import frc.robot.commands.Manual;
import frc.robot.commands.PositionWheel;
import frc.robot.commands.SpinWheelofF;
import frc.robot.commands.VisionRotate;
import frc.robot.commands.drivetrain.VoltDrive;
import frc.robot.subsystems.CtrlPanelOperator;
import frc.robot.subsystems.leds.LED;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrains.QuasarDrivetrain;
import frc.robot.systemtests.drivetrain.*;

import java.util.HashMap;
import java.util.function.DoubleSupplier;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class QuasarContainer extends LightningContainer {
  private final LightningDrivetrain drivetrain = new QuasarDrivetrain();
  private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
  private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);
  private final Vision vision = new Vision();
  private final LED led = new LED();
  private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
  private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);
  private final CtrlPanelOperator WheelofFortune = new CtrlPanelOperator();

  private final AutonGenerator autonGenerator = new AutonGenerator(drivetrain /*, null, null, null*/ );

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public QuasarContainer() {
    // Configure the button bindings
    configureSystemTests();
    configureButtonBindings();
    initializeDashboardCommands();

    drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -driver.getY(GenericHID.Hand.kLeft),
        () -> -driver.getY(GenericHID.Hand.kRight)));
    WheelofFortune.setDefaultCommand(new Manual(WheelofFortune,getCtrlPower()));
  }

  private void initializeDashboardCommands() {
    SmartDashboard.putData("vision rotate", new VisionRotate(drivetrain,vision));
    //SmartDashboard.putData("go blue", new InstantCommand(() -> led.goOrangeAndBlue(), led));
    //SmartDashboard.putData("off", new InstantCommand(() -> led.goOff(), led));
    SmartDashboard.putData("Spin the wheel", new SpinWheelofF(WheelofFortune));
    SmartDashboard.putData("Position WoF", new PositionWheel(WheelofFortune));
  }

  private void configureSystemTests() {
    SystemTest.register(new LeftSideMoves(drivetrain));
    SystemTest.register(new RightSideMoves(drivetrain));
    SystemTest.register(new MoveMasters(drivetrain));
    SystemTest.register(new MovePrimarySlaves(drivetrain));
    SystemTest.register(new MoveSecondarySlaves(drivetrain));
  }

  @Override
  public HashMap<String, Command> getAutonomousCommands() { return autonGenerator.getCommands(); }

  // private Command getPathCMD() {
  //   return (new SequentialCommandGroup(PathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH),
  //       new InstantCommand(drivetrain::resetSensorVals, drivetrain), new WaitCommand(1d),
  //       PathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH_TWO)));
  //   // return pathGenerator.getRamseteCommand(drivetrain, Paths.TEST_PATH);
  // }

  @Override
  public void configureDefaultCommands() {
    // TODO Auto-generated method stub

  }

  @Override
  public void releaseDefaultCommands() {
    // TODO Auto-generated method stub

  }
private DoubleSupplier getCtrlPower(){
    return ()-> operator.getTriggerAxis(GenericHID.Hand.kRight);
}
  @Override
  public LightningDrivetrain getDrivetrain() {
    return drivetrain;
  }

  @Override
  public void configureButtonBindings() {
  }

}
