/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.robots;

import java.util.HashMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.DrivetrainLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.SmartDashDrivetrain;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.auton.AutonGenerator;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.drivetrain.VelocityTankDrive;
import frc.robot.commands.drivetrain.VoltDrive;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.CtrlPanelOperator;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Logging;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrains.GregDrivetrain;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class GregContainer extends LightningContainer {

    private static int powerCellCapacity = 0;

    private final LightningDrivetrain drivetrain = new GregDrivetrain();
    private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
    private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);

    private final Logging loggerSystem = new Logging();
    private final Vision vision = new Vision();

    // private final Collector collector = new Collector();
    // private final Indexer indexer = Indexer.create();
    // private final Shooter shooter = new Shooter();

    // private final Climber climber = new Climber();
    // private final CtrlPanelOperator jeopardyWheel = new CtrlPanelOperator();

    private final Joystick driverLeft = new Joystick(JoystickConstants.DRIVER_LEFT);
    private final Joystick driverRight = new Joystick(JoystickConstants.DRIVER_RIGHT);
    private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);

    private final AutonGenerator autonGenerator = new AutonGenerator(drivetrain /*, null, null, null*/ );

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public GregContainer(int startingPowerCellCapacity) {

        powerCellCapacity = startingPowerCellCapacity;

        // Configure the button bindings
        configureButtonBindings();
        initializeDashboardCommands();

        drivetrain.setDefaultCommand(new VoltDrive(drivetrain,
                                     () -> -driverLeft.getY(),
                                     () -> -driverRight.getY()
        ));

    }

    private void initializeDashboardCommands() {}

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
    public HashMap<String, Command> getAutonomousCommands() { return autonGenerator.getCommands(); }

    public static int getPowerCellCapacity() {
        return powerCellCapacity;
    }

    public static void setPowerCellCapacity(int newPowerCellCapacity) {
        powerCellCapacity = newPowerCellCapacity;
    }

    @Override
  public void configureDefaultCommands() {}

  @Override
  public void releaseDefaultCommands() {}

  @Override
  public LightningDrivetrain getDrivetrain() { return drivetrain; }

}
