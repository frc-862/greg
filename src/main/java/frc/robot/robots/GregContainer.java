/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.robots;

import edu.wpi.first.hal.sim.PCMSim;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lightning.LightningContainer;
import frc.lightning.commands.RumbleCommand;
import frc.lightning.subsystems.DrivetrainLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.SmartDashDrivetrain;
import frc.robot.JoystickConstants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.auton.AutonGenerator;
import frc.robot.commands.*;
import frc.robot.commands.drivetrain.VoltDrive;
import frc.robot.commands.shooter.FireThree;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drivetrains.GregDrivetrain;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class GregContainer extends LightningContainer {

    private final LightningDrivetrain drivetrain = new GregDrivetrain();
    private final DrivetrainLogger drivetrainLogger = new DrivetrainLogger(drivetrain);
    private final SmartDashDrivetrain smartDashDrivetrain = new SmartDashDrivetrain(drivetrain);
    private final DigitalInput sensors[] = new DigitalInput[0];

    private final Logging loggerSystem = new Logging();
    private final Vision vision = new Vision();
    private final Indexer indexer = new Indexer(sensors);
    private final Collector collector = new Collector();
    private final PCMSim pcmSim = new PCMSim(RobotMap.COMPRESSOR_ID);
    private final Climber climber = new Climber(); 

    private final Shooter shooter = new Shooter();
    private final ShooterAngle shooterAngle = new ShooterAngle();

    // private final Climber climber = new Climber();
    // private final CtrlPanelOperator jeopardyWheel = new CtrlPanelOperator();

    private final Joystick driverLeft = new Joystick(JoystickConstants.DRIVER_LEFT);
    private final Joystick driverRight = new Joystick(JoystickConstants.DRIVER_RIGHT);
    private final XboxController operator = new XboxController(JoystickConstants.OPERATOR);
    private final Joystick climberController = new Joystick(JoystickConstants.CLIMBER); 

    private final AutonGenerator autonGenerator = new AutonGenerator(drivetrain,collector, indexer, shooter, shooterAngle, vision);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public GregContainer() {

        // Configure the button bindings
        configureButtonBindings();
        initializeDashboardCommands();

        drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -driverLeft.getY(), () -> -driverRight.getY()));
        indexer.setDefaultCommand(new IndexerCommand(indexer));
        collector.setDefaultCommand(new Collect(collector, this::getCollectPower));
        if(ShooterAngle.MANUAL_CONTROL) shooterAngle.setDefaultCommand(new RunCommand(() -> shooterAngle.setPower(-operator.getY(GenericHID.Hand.kLeft)), shooterAngle));

        final var flyWheelSpeed = Shuffleboard.getTab("Shooter").add("SetPoint", 1)
                .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 4000)) // specify widget properties here
                .getEntry();
        flyWheelSpeed.addListener((n) -> {
            shooter.setShooterVelocity(flyWheelSpeed.getDouble(0));
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        // final var flyWheelAngle = Shuffleboard.getTab("Shooter").add("SetAngle", 100)
        //         .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 80, "max", 155)) // specify widget properties here
        //         .getEntry();
        // flyWheelAngle.addListener((n) -> {
        //      shooterAngle.setDesiredAngle(flyWheelAngle.getDouble(100));
        // }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        // shooter.setWhenBallShot((n) -> shooter.shotBall());

        climber.setDefaultCommand(new ManualClimb(climber, () -> -climberController.getRawAxis(1), 
                                                           () -> -climberController.getRawAxis(5)));
    }

    private void initializeDashboardCommands() {
        SmartDashboard.putBoolean("Happy", false);
        SmartDashboard.putData("vision rotate", new VisionRotate(drivetrain, vision));
        SmartDashboard.putData("Ring 1 on", new InstantCommand(() -> vision.ringOn()));
        SmartDashboard.putData("Ring 2 on", new InstantCommand(() -> vision.bothRingsOn()));
        SmartDashboard.putData("Ring off", new InstantCommand(() -> vision.ringOff()));
        SmartDashboard.putData("safety in", new InstantCommand(() -> indexer.safteyClosed()));
        SmartDashboard.putData("safety out", new InstantCommand(() -> indexer.safteyOpen()));
        SmartDashboard.putData("zero balls held",new InstantCommand(() ->indexer.reastBallsHeld()));
        SmartDashboard.putData("collect",
                new CollectEject(collector, () -> operator.getTriggerAxis(GenericHID.Hand.kRight),
                        () -> operator.getTriggerAxis(GenericHID.Hand.kLeft)));
        SmartDashboard.putData("ResetBallCount", new InstantCommand(indexer::resetBallCount, indexer));
        SmartDashboard.putData("ResetPose", new InstantCommand(drivetrain::resetSensorVals, drivetrain));
        SmartDashboard.putData("Fire 3", new FireThree(shooter, indexer, shooterAngle, vision, collector));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    @Override
    public void configureButtonBindings() {
        (new Trigger((() -> operator.getTriggerAxis(GenericHID.Hand.kRight) > 0.03))).whenActive(new InstantCommand(() -> { if(!collector.isOut()) collector.puterOuterIn(); }, collector));
        (new JoystickButton(operator, JoystickConstants.RIGHT_BUMPER)).whenPressed(new InstantCommand(collector::toggleCollector, collector));
        (new JoystickButton(operator, JoystickConstants.Y)) .whenPressed(new InstantCommand(indexer::toggleSaftey, indexer));
        (new JoystickButton(operator, JoystickConstants.LEFT_BUMPER)).whileHeld(indexer::spit, indexer);
        (new JoystickButton(operator, JoystickConstants.START)).whenPressed(() -> { indexer.resetBallCount(); shooter.resetBallsFired(); }, indexer, shooter);
        (new JoystickButton(operator, JoystickConstants.BACK)).whileHeld(indexer::toShooter, indexer);
        (new JoystickButton(driverLeft, 1)).whileHeld(new VisionRotate(drivetrain,vision));
        (new JoystickButton(climberController, JoystickConstants.A)).whileHeld(climber::up, climber);
        (new JoystickButton(climberController, JoystickConstants.B)).whileHeld(climber::down, climber);
        (new JoystickButton(driverRight, 1)).whileHeld(new FullAutoFireOne(drivetrain,vision,shooter,shooterAngle,indexer,true));
        (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()->shooter.stop(),shooter));

        (new JoystickButton(operator, JoystickConstants.X)).whenPressed(new InstantCommand(vision::biasReset));
        (new POVButton(operator, 0)).whenPressed(new RumbleCommand(operator, vision::biasUp));
        (new POVButton(operator, 90)).whenPressed(new RumbleCommand(operator, vision::biasRight));
        (new POVButton(operator, 180)).whenPressed(new RumbleCommand(operator, vision::biasDown));
        (new POVButton(operator, 270)).whenPressed(new RumbleCommand(operator, vision::biasLeft));
    }

    @Override
    public HashMap<String, Command> getAutonomousCommands() { return autonGenerator.getCommands(); }

    public double getCollectPower() {
        return operator.getTriggerAxis(GenericHID.Hand.kRight) - operator.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    @Override
    public void configureDefaultCommands() {}

    @Override
    public void releaseDefaultCommands() {}

    @Override
    public LightningDrivetrain getDrivetrain() { return drivetrain; }

}
