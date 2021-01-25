/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.containers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.LightningConfig;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.commands.CollectEject;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.drivetrain.VelocityTankDrive;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.PrototypeShooter;
import frc.robot.subsystems.drivetrains.NebulaDrivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class NebulaContainer extends LightningContainer {
  private final LightningDrivetrain drivetrain = NebulaDrivetrain.create();
 // private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
 // private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);

  private final Collector collector = new Collector();
  private final PrototypeShooter prototypeShooter = new PrototypeShooter();

  private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
  private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public NebulaContainer() {
    // Configure the button bindings
    configureButtonBindings();
    initializeDashboardCommands();
    // collector.setDefaultCommand(new
    // CollectEject(collector,driver.getTriggerAxis(GenericHID.Hand.kLeft),driver.getTriggerAxis(GenericHID.Hand.kLeft)));

    drivetrain.setDefaultCommand(new TankDrive(drivetrain, () -> -driver.getY(GenericHID.Hand.kLeft),
        () -> -driver.getY(GenericHID.Hand.kRight)));

    System.out.println("Building Nebula");
    prototypeShooter.setDefaultCommand(new RunCommand(() -> {
      prototypeShooter.setVelocityMShootoer(SmartDashboard.getNumber("Shooter RPM", 0));
    }, prototypeShooter));

    collector.setDefaultCommand(
        new CollectEject(collector, () -> driver.getTriggerAxis(Hand.kRight), () -> driver.getTriggerAxis(Hand.kLeft)));

  }

  protected void initializeDashboardCommands() {
    double var = SmartDashboard.getNumber("Shooter RPM", 0);
    SmartDashboard.putData("OpenLoop", new TankDrive(drivetrain, () -> -driver.getY(GenericHID.Hand.kLeft),
        () -> -driver.getY(GenericHID.Hand.kRight)));

    SmartDashboard.putData("ClosedLoop", new VelocityTankDrive(drivetrain, () -> -driver.getY(GenericHID.Hand.kLeft),
        () -> -driver.getY(GenericHID.Hand.kRight)

    ));
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
  public void configureDefaultCommands() {
  }

  @Override
  public void releaseDefaultCommands() {
  }

  @Override
  public LightningDrivetrain getDrivetrain() {
    return drivetrain;
  }

  protected void configureSystemTests(){};

  public LightningConfig getConfig(){ return null; }
  
  protected void configureAutonomousCommands(){};
}
