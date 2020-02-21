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
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.lightning.LightningContainer;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.commands.ledcommands.BlinkGreen;
import frc.robot.commands.ledcommands.BlinkRed;
import frc.robot.commands.ledcommands.BlinkYellow;
import frc.robot.commands.ledcommands.Pong;
import frc.robot.commands.ledcommands.RunLeds;
import frc.robot.commands.ledcommands.SolidGreen;
import frc.robot.commands.ledcommands.SolidRed;
import frc.robot.commands.ledcommands.SolidYellow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.leds.AddressableLEDMatrix;
import frc.robot.subsystems.leds.LEDMatrixMap;
import frc.robot.subsystems.leds.LEDs;
import frc.robot.subsystems.leds.PongFunctions;
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
    public static final LEDMatrixMap ledMatrixMap = new LEDMatrixMap();
    public static final AddressableLEDMatrix addressableLEDMatrix = new AddressableLEDMatrix(8, 32, 1, 150, 9);

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
        //(new JoystickButton(driver, Button.kA.value)).whenPressed(new BlinkGreen(() -> 17, Leds));
        //(new JoystickButton(driver, Button.kA.value)).whenPressed(new BlinkYellow(() -> 17, Leds));
        //(new JoystickButton(driver, Button.kA.value)).whenPressed(new SolidYellow(() -> 1, Leds));
        //(new JoystickButton(driver, Button.kA.value)).whenPressed(new SolidGreen(() -> 9, Leds));
        (new JoystickButton(driver, Button.kA.value)).whenPressed(new Pong(Leds, ledMatrixMap, addressableLEDMatrix));
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
