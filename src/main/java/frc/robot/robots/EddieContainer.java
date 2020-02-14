/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.robots;

import java.util.HashMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.commands.ledcommands.RunLeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.drivetrains.EddieDrivetrain;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class EddieContainer extends LightningContainer {

    private static int powerCellCapacity = 0;

    public static final LEDs Leds = new LEDs();

    //private final Logging loggerSystem = new Logging();
    //private final Vision vision = new Vision();

    //private final Collector collector = new Collector();
    //private final Indexer indexer = Indexer.create();
    //private final Shooter shooter = new Shooter();

    //private final Climber climber = new Climber();
    //private final CtrlPanelOperator jeopardyWheel = new CtrlPanelOperator();


    private final XboxController driver = new XboxController(JoystickConstants.DRIVER);
    //private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);

    //private final LightningDrivetrain drivetrain = new EddieDrivetrain();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    //public EddieContainer(int startingPowerCellCapacity) {

    private static final LightningDrivetrain drivetrain = new EddieDrivetrain();

    
    public EddieContainer() {
        //powerCellCapacity = startingPowerCellCapacity;

        // Configure the button bindings
        configureButtonBindings();
        //l
        //l
        //l
        //JoystickButton exampleButton = new JoystickButton(driver, 1);
        //exampleButton.whenPressed(new Aim(drivetrain, vision, () -> 0));
    }



    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    @Override
    public void configureButtonBindings() {
        //(new JoystickButton(driver, Button.kA.value)).whenPressed(new InstantCommand(() -> Leds.ToggleLED(1), Leds));
        //(new JoystickButton(driver, Button.kB.value)).whenPressed(new InstantCommand(() -> Leds.ToggleLED(2), Leds));
        //(new JoystickButton(driver, Button.kY.value)).whenPressed(new InstantCommand(() -> Leds.ToggleLED(3), Leds));
        //(new JoystickButton(driver, Button.kX.value)).whenPressed(new InstantCommand(() -> Leds.ToggleLED(4), Leds));
        //(new JoystickButton(driver, Button.kA.value)).whenReleased(new RunLeds());
        //(new JoystickButton(driver, Button.kB.value)).whenPressed(new RunLeds());
        //(new JoystickButton(driver, Button.kY.value)).whenPressed(new RunLeds());
        (new JoystickButton(driver, Button.kA.value)).whenPressed(new RunLeds());
    }

    @Override
    public HashMap<String, Command> getAutonomousCommands() {
        Command[] empty = {};
        return null;
    }

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
    public LightningDrivetrain getDrivetrain() {
        return drivetrain;
    }

  //@Override
  //public LightningDrivetrain getDrivetrain() { return drivetrain; }

}
