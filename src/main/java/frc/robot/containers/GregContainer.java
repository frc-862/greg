package frc.robot.containers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.Arrays;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.GenericHID;
import frc.lightning.LightningConfig;
import frc.lightning.LightningContainer;
import frc.lightning.auto.Autonomous;
import frc.lightning.auto.Path;
import frc.lightning.auto.Paths;
import frc.lightning.commands.RumbleCommand;
import frc.lightning.commands.VoltDrive;
import frc.robot.commands.auto.GalacticSearchCommand;
import frc.lightning.subsystems.*;
import frc.lightning.subsystems.IMU;
import frc.robot.JoystickConstants;
import frc.robot.commands.Collect;
import frc.robot.commands.CollectEject;
import frc.robot.commands.FullAutoFireOne;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.ManualClimb;
import frc.robot.commands.VisionRotate;
import frc.robot.commands.shooter.FireThree;
import frc.robot.config.GregConfig;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drivetrains.GregDrivetrain;

public class GregContainer extends LightningContainer {

    // CONFIG FILE
    private static final LightningConfig config = new GregConfig();

    // SUBSYSTEMS
    private static final IMU imu = IMU.navX();
    private static final Vision vision = new Vision();
    private static final LightningDrivetrain drivetrain = new GregDrivetrain(config, imu.heading(), imu.zero());
    private static final Collector collector = new Collector();
    private static final Indexer indexer = new Indexer();
    private static final Shooter shooter = new Shooter();
    private static final ShooterAngle shooterAngle = new ShooterAngle();
    private static final Climber climber = new Climber();

    // LOGGERS
    private static final BaseRobotLogger robotLogger = new BaseRobotLogger(drivetrain, imu);
    private static final ShuffleboardBaseRobotDisplay robotDash = new ShuffleboardBaseRobotDisplay(drivetrain, imu);

    // JOYSTICKS
    private static final Joystick driverLeft = new Joystick(JoystickConstants.DRIVER_LEFT);
    private static final Joystick driverRight = new Joystick(JoystickConstants.DRIVER_RIGHT);
    private static final XboxController operator = new XboxController(JoystickConstants.OPERATOR);
    private static final Joystick climberController = new Joystick(JoystickConstants.CLIMBER);
    private static final XboxController testController = new XboxController(0);

    public GregContainer() {
        super();
    }

    @Override
    protected void configureButtonBindings() {

        // DRIVER
        // (new JoystickButton(driverLeft, 1)).whileHeld(new VisionRotate(drivetrain,vision));
        // (new JoystickButton(driverRight, 1)).whileHeld(new FullAutoFireOne(drivetrain,vision,shooter,shooterAngle,indexer,true));
        // // (new JoystickButton(driverRight, 1)).whenPressed(new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer));
        // (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()->shooter.stop(),shooter));

        // OPERATOR
        // (new Trigger((() -> operator.getTriggerAxis(GenericHID.Hand.kRight) > 0.03))).whenActive(new InstantCommand(() -> { if(!collector.isOut()) collector.puterOuterIn(); }, collector));
        // (new JoystickButton(operator, JoystickConstants.RIGHT_BUMPER)).whenPressed(new InstantCommand(collector::toggleCollector, collector));
        // (new JoystickButton(operator, JoystickConstants.Y)) .whenPressed(new InstantCommand(indexer::toggleSaftey, indexer));
        // (new JoystickButton(operator, JoystickConstants.LEFT_BUMPER)).whileHeld(indexer::spit, indexer);
        // (new JoystickButton(operator, JoystickConstants.START)).whenPressed(() -> { indexer.reastBallsHeld(); }, indexer, shooter);
        // (new JoystickButton(operator, JoystickConstants.BACK)).whileHeld(indexer::toShooter, indexer);
        // (new JoystickButton(operator, JoystickConstants.X)).whenPressed(new InstantCommand(vision::biasReset));
        // (new POVButton(operator, 0)).whenPressed(new RumbleCommand(operator, vision::biasUp));
        // (new POVButton(operator, 90)).whenPressed(new RumbleCommand(operator, vision::biasRight));
        // (new POVButton(operator, 180)).whenPressed(new RumbleCommand(operator, vision::biasDown));
        // (new POVButton(operator, 270)).whenPressed(new RumbleCommand(operator, vision::biasLeft));

        // CLIMB CONTROLLER
        // (new JoystickButton(climberController, JoystickConstants.A)).whileHeld(climber::up, climber);
        // (new JoystickButton(climberController, JoystickConstants.B)).whileHeld(climber::down, climber);

        // TEST CONTROLLER
        (new Trigger((() -> testController.getTriggerAxis(GenericHID.Hand.kRight) > 0.03))).whenActive(new InstantCommand(() -> { if(!collector.isOut()) collector.puterOuterIn(); }, collector));
        (new JoystickButton(testController, JoystickConstants.RIGHT_BUMPER)).whenPressed(new InstantCommand(collector::toggleCollector, collector));
        (new JoystickButton(testController, JoystickConstants.Y)) .whenPressed(new InstantCommand(indexer::toggleSaftey, indexer));
        (new JoystickButton(testController, JoystickConstants.LEFT_BUMPER)).whileHeld(indexer::spit, indexer);
        (new JoystickButton(testController, JoystickConstants.START)).whenPressed(() -> { indexer.reastBallsHeld(); }, indexer, shooter);
        (new JoystickButton(testController, JoystickConstants.BACK)).whileHeld(indexer::toShooter, indexer);
        (new JoystickButton(testController, JoystickConstants.X)).whenPressed(new InstantCommand(vision::biasReset));
        (new POVButton(testController, 0)).whenPressed(new RumbleCommand(testController, vision::biasUp));
        (new POVButton(testController, 90)).whenPressed(new RumbleCommand(testController, vision::biasRight));
        (new POVButton(testController, 180)).whenPressed(new RumbleCommand(testController, vision::biasDown));
        (new POVButton(testController, 270)).whenPressed(new RumbleCommand(testController, vision::biasLeft));
        (new JoystickButton(testController, JoystickConstants.A)).whileHeld(climber::up, climber);
        (new JoystickButton(testController, JoystickConstants.B)).whileHeld(climber::down, climber);
        (new JoystickButton(testController, JoystickConstants.A)).whenReleased(climber::stop, climber);
        (new JoystickButton(testController, JoystickConstants.B)).whenReleased(climber::stop, climber);

    }

    @Override
    protected void configureSystemTests() {

    }

    @Override
    protected void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -testController.getY(GenericHID.Hand.kLeft), () -> -testController.getY(GenericHID.Hand.kRight)));
        // drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -driverLeft.getY(), () -> -driverRight.getY()));
        indexer.setDefaultCommand(new IndexerCommand(indexer));
        collector.setDefaultCommand(new Collect(collector, this::getCollectPower));
        // climber.setDefaultCommand(new ManualClimb(climber, () -> -climberController.getRawAxis(1), () -> -climberController.getRawAxis(5)));
        // //shooter.setWhenBallShot((n) -> shooter.shotBall());
        // //shooterAngle.setDefaultCommand(new RunCommand(() -> shooterAngle.setPower(-operator.getY(GenericHID.Hand.kLeft)), shooterAngle));
    }

    @Override
    protected void releaseDefaultCommands() {

    }

    @Override
    protected void initializeDashboardCommands() {

        // SHOOTER DASHBOARD DISPLAY
        final var flyWheelSpeed = Shuffleboard.getTab("Shooter").add("SetPoint", 1)
                .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 4000)).getEntry();
        flyWheelSpeed.addListener((n) -> {
            shooter.setShooterVelocity(flyWheelSpeed.getDouble(0));
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        // SHOOTER ANGLE DASHBOARD
        // final var flyWheelAngle = Shuffleboard.getTab("Shooter").add("SetAngle", 100)
        //         .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 80, "max", 155)).getEntry();
        // flyWheelAngle.addListener((n) -> {
        //     shooterAngle.setAngle(flyWheelAngle.getDouble(100)); // setDesiredAngle?
        // }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        // GENERAL DASHBOARD COMMANDS
        final var vision_tab = Shuffleboard.getTab("Vision");
        vision_tab.add("Vision Rotate", new VisionRotate(drivetrain, vision));
        vision_tab.add("Ring 1 on", new InstantCommand(vision::ringOn));
        vision_tab.add("Ring 2 on", new InstantCommand(vision::bothRingsOn));
        vision_tab.add("Ring off", new InstantCommand(vision::ringOff));

        final var indexer_tab = Shuffleboard.getTab("Indexer");
        indexer_tab.add("safety in", new InstantCommand(indexer::safteyClosed));
        indexer_tab.add("safety out", new InstantCommand(indexer::safteyOpen));
        indexer_tab.add("zero balls held", new InstantCommand(indexer::resetBallCount));
        indexer_tab.add("collect",  new CollectEject(collector, () -> operator.getTriggerAxis(GenericHID.Hand.kRight), () -> operator.getTriggerAxis(GenericHID.Hand.kLeft)));
        indexer_tab.add("Fire 3", new FireThree(shooter, indexer, shooterAngle, vision, collector));

        final var pose_tab = Shuffleboard.getTab("Pose");
        pose_tab.add("ResetPose", new InstantCommand(drivetrain::resetSensorVals, drivetrain));

        final var shooter_tab = Shuffleboard.getTab("Shooter");
        shooter_tab.add("Manual shooter angle", new RunCommand(() -> shooterAngle.setPower(-operator.getY(GenericHID.Hand.kLeft)), shooterAngle));
        shooter_tab.add("Fire 3", new FireThree(shooter, indexer, shooterAngle, vision, collector));

    }

    @Override
    protected void configureAutonomousCommands() {

        // CONFIGURE PATHS
        Paths.register(new Path("Blue A", "paths/output/BlueA.wpilib.json"));
        Paths.register(new Path("Blue B", "paths/output/BlueB.wpilib.json"));
        Paths.register(new Path("Red A", "paths/output/RedA.wpilib.json"));
        Paths.register(new Path("Red B", "paths/output/RedB.wpilib.json"));
        Paths.register(new Path("Barrel Racing", "paths/output/BarrelRacing.wpilib.json"));
        Paths.register(new Path("Slalom", "paths/output/Slalom.wpilib.json"));
        Paths.register(new Path("Bounce 1", "paths/output/Bounce1.wpilib.json"));
        Paths.register(new Path("Bounce 2", "paths/output/Bounce2.wpilib.json", true));
        Paths.register(new Path("Bounce 3", "paths/output/Bounce3.wpilib.json"));
        Paths.register(new Path("Bounce 4", "paths/output/Bounce4.wpilib.json", true));
        Paths.register(new Path("Interstellar Green", "paths/output/InterstellarGreen.wpilib.json"));
        Paths.register(new Path("Interstellar Yellow Fwr", "paths/output/InterstellarYellow.wpilib.json"));
        Paths.register(new Path("Interstellar Yellow Back", "paths/output/InterstellarYellow.wpilib.json", true));
        Paths.register(new Path("Interstellar Blue Fwr", "paths/output/InterstellarBlue.wpilib.json"));
        Paths.register(new Path("Interstellar Blue Back", "paths/output/InterstellarBlue.wpilib.json", true));
        Paths.register(new Path("Interstellar Red Fwr", "paths/output/InterstellarRed.wpilib.json"));
        Paths.register(new Path("Interstellar Red Back", "paths/output/InterstellarRed.wpilib.json", true));
        Paths.register(new Path("Test PathWeaver", "paths/output/TestFwd.wpilib.json"));
        Paths.register(new Path("Test Path", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                            new Pose2d(0.75d, 0d, Rotation2d.fromDegrees(0d)))));

        // CONFIGURE AUTON COMMANDS
        Autonomous.register("Test Auton Driving", Paths.getPathCommand(drivetrain, "Test Path"));
        Autonomous.register("Test PathWeaver Paths", Paths.getPathCommand(drivetrain, "Test PathWeaver"));
        Autonomous.register("Slalom", Paths.getPathCommand(drivetrain, "Slalom"));
        Autonomous.register("Barrel Racing", Paths.getPathCommand(drivetrain, "Barrel Racing"));
        Autonomous.register("Bounce Path", new SequentialCommandGroup(
            Paths.getPathCommand(drivetrain, "Bounce 1"),
            Paths.getPathCommand(drivetrain, "Bounce 2"),
            Paths.getPathCommand(drivetrain, "Bounce 3"),
            Paths.getPathCommand(drivetrain, "Bounce 4")
        ));
        Autonomous.register("Galactic Search", new GalacticSearchCommand(drivetrain, collector, indexer));
    }

    @Override
    public LightningConfig getConfig() {
        return config;
    }

    @Override
    public LightningDrivetrain getDrivetrain() {
        return drivetrain;
    }

    public double getCollectPower() {
        return testController.getTriggerAxis(GenericHID.Hand.kRight) - testController.getTriggerAxis(GenericHID.Hand.kLeft);
        // return operator.getTriggerAxis(GenericHID.Hand.kRight) - operator.getTriggerAxis(GenericHID.Hand.kLeft);
    }

}
