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
import java.util.List;
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
import frc.robot.commands.auto.InterStellarAccuracyCommand;
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
    // private static final XboxController testController = new XboxController(0);


    // WAYPOINTS
    private static final List<Pose2d> greenWaypoints = Arrays.asList(new Pose2d(8d, 2.286d, Rotation2d.fromDegrees(0d)), new Pose2d(0.75d, 2.286d, Rotation2d.fromDegrees(0d)));
    private static final List<Pose2d> yellowWaypoints = Arrays.asList(new Pose2d(5.5d, 2.286d, Rotation2d.fromDegrees(0d)), new Pose2d(0.75d, 2.286d, Rotation2d.fromDegrees(0d)));
    private static final List<Pose2d> blueWaypoints = Arrays.asList(new Pose2d(3.96d, 2.286d, Rotation2d.fromDegrees(0d)), new Pose2d(0.75d, 2.286d, Rotation2d.fromDegrees(0d)));
    private static final List<Pose2d> redWaypoints = Arrays.asList(new Pose2d(3.44d, 2.286d, Rotation2d.fromDegrees(0d)), new Pose2d(0.75d, 2.286d, Rotation2d.fromDegrees(0d)));

    public GregContainer() {
        super();
    }

    @Override
    protected void configureButtonBindings() {

        // DRIVER
        // (new JoystickButton(driverLeft, 1)).whileHeld(new VisionRotate(drivetrain,vision));
        (new JoystickButton(driverRight, 1)).whileHeld(new FullAutoFireOne(drivetrain,vision,shooter,shooterAngle,indexer,true));
        // (new JoystickButton(driverRight, 1)).whenPressed(new FullAutoFireMagazine(drivetrain, vision, shooter, shooterAngle, indexer));
        (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()->shooter.stop(),shooter));

        // OPERATOR
        (new Trigger((() -> operator.getTriggerAxis(GenericHID.Hand.kRight) > 0.03))).whenActive(new InstantCommand(() -> { if(!collector.isOut()) collector.puterOuterIn(); }, collector));
        (new JoystickButton(operator, JoystickConstants.RIGHT_BUMPER)).whenPressed(new InstantCommand(collector::toggleCollector, collector));
        (new JoystickButton(operator, JoystickConstants.Y)) .whenPressed(new InstantCommand(indexer::toggleSaftey, indexer));
        (new JoystickButton(operator, JoystickConstants.LEFT_BUMPER)).whileHeld(indexer::spit, indexer);
        (new JoystickButton(operator, JoystickConstants.START)).whenPressed(() -> { indexer.reastBallsHeld(); }, indexer, shooter);
        (new JoystickButton(operator, JoystickConstants.BACK)).whileHeld(indexer::toShooter, indexer);
        (new JoystickButton(operator, JoystickConstants.X)).whenPressed(new InstantCommand(vision::biasReset));
        (new POVButton(operator, 0)).whenPressed(new RumbleCommand(operator, vision::biasUp));
        (new POVButton(operator, 90)).whenPressed(new RumbleCommand(operator, vision::biasRight));
        (new POVButton(operator, 180)).whenPressed(new RumbleCommand(operator, vision::biasDown));
        (new POVButton(operator, 270)).whenPressed(new RumbleCommand(operator, vision::biasLeft));

        // CLIMB CONTROLLER
        // (new JoystickButton(climberController, JoystickConstants.A)).whileHeld(climber::up, climber);
        // (new JoystickButton(climberController, JoystickConstants.B)).whileHeld(climber::down, climber);

        // TEST CONTROLLER
        // (new Trigger((() -> testController.getTriggerAxis(GenericHID.Hand.kRight) > 0.03))).whenActive(new InstantCommand(() -> { if(!collector.isOut()) collector.puterOuterIn(); }, collector));
        // (new JoystickButton(testController, JoystickConstants.RIGHT_BUMPER)).whenPressed(new InstantCommand(collector::toggleCollector, collector));
        // (new JoystickButton(testController, JoystickConstants.Y)) .whenPressed(new InstantCommand(indexer::toggleSaftey, indexer));
        // (new JoystickButton(testController, JoystickConstants.LEFT_BUMPER)).whileHeld(indexer::spit, indexer);
        // (new JoystickButton(testController, JoystickConstants.START)).whenPressed(() -> { indexer.reastBallsHeld(); }, indexer, shooter);
        // (new JoystickButton(testController, JoystickConstants.BACK)).whileHeld(indexer::toShooter, indexer);
        // (new JoystickButton(testController, JoystickConstants.X)).whenPressed(new InstantCommand(vision::biasReset));
        // (new POVButton(testController, 0)).whenPressed(new RumbleCommand(testController, vision::biasUp));
        // (new POVButton(testController, 90)).whenPressed(new RumbleCommand(testController, vision::biasRight));
        // (new POVButton(testController, 180)).whenPressed(new RumbleCommand(testController, vision::biasDown));
        // (new POVButton(testController, 270)).whenPressed(new RumbleCommand(testController, vision::biasLeft));
        // (new JoystickButton(testController, JoystickConstants.A)).whileHeld(climber::up, climber);
        // (new JoystickButton(testController, JoystickConstants.B)).whileHeld(climber::down, climber);
        // (new JoystickButton(testController, JoystickConstants.A)).whenReleased(climber::stop, climber);
        // (new JoystickButton(testController, JoystickConstants.B)).whenReleased(climber::stop, climber);

    }

    @Override
    protected void configureSystemTests() {

    }

    @Override
    protected void configureDefaultCommands() {
        // drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -testController.getY(GenericHID.Hand.kLeft), () -> -testController.getY(GenericHID.Hand.kRight)));
        drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -driverLeft.getY(), () -> -driverRight.getY()));
        indexer.setDefaultCommand(new IndexerCommand(indexer));
        collector.setDefaultCommand(new Collect(collector, this::getCollectPower));
        climber.setDefaultCommand(new ManualClimb(climber, () -> -climberController.getRawAxis(1), () -> -climberController.getRawAxis(5)));
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

        //CONFIGURE PATHS
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
        Paths.register(new Path("Straight", "paths/output/Straight.wpilib.json"));
        Paths.register(new Path("TurnLeft", "paths/output/TurnLeft.wpilib.json"));
        Paths.register(new Path("TurnRight", "paths/output/TurnRight.wpilib.json"));
        Paths.register(new Path("CurveLeft", "paths/output/CurveLeft.wpilib.json"));
        Paths.register(new Path("CurveRight", "paths/output/CurveRight.wpilib.json"));
        Paths.register(new Path("Test PathWeaver", "paths/output/TestFwd.wpilib.json"));
        Paths.register(new Path("Test Path", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                            new Pose2d(0.75d, 0d, Rotation2d.fromDegrees(0d)))));

        Paths.register(new Path("Manual Blue A", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(13.605d, 0.157d, Rotation2d.fromDegrees(42.306d)),
                                                            new Pose2d(14.237d, 7.511d, Rotation2d.fromDegrees(8.746d)),
                                                            new Pose2d(21.055d, 5.087d, Rotation2d.fromDegrees(-14.524d)),
                                                            new Pose2d(27.69d, 3.937d, Rotation2d.fromDegrees(0.0d)))));
        
        Paths.register(new Path("Manual Blue B", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(12.738d, -1.022d, Rotation2d.fromDegrees(28.824d)),
                                                            new Pose2d(18.861d, 4.967d, Rotation2d.fromDegrees(-12.51d)),
                                                            new Pose2d(23.486d, 0.375d, Rotation2d.fromDegrees(-36.236d)),
                                                            new Pose2d(28.088d, -2.212d, Rotation2d.fromDegrees(-26.565d)))));

        Paths.register(new Path("Manual Red A", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(7.033d, -0.133d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(12.164d, -2.566d, Rotation2d.fromDegrees(4.53d)),
                                                            new Pose2d(14.622d, 5.097d, Rotation2d.fromDegrees(50.548d)),
                                                            new Pose2d(28.221d, 5.087d, Rotation2d.fromDegrees(-4.399d)))));

        Paths.register(new Path("Manual Red B", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(5.74d, -0.704d, Rotation2d.fromDegrees(-27.198d)),
                                                            new Pose2d(10.598d, -6.228d, Rotation2d.fromDegrees(-1.637d)),
                                                            new Pose2d(15.822d, -1.203d, Rotation2d.fromDegrees(25.712d)),
                                                            new Pose2d(27.248d, -2.654d, Rotation2d.fromDegrees(0.0d)))));

        Paths.register(new Path("Manual BarrelRacing", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(16.327d)),
                                                            new Pose2d(5.69d, 1.098d, Rotation2d.fromDegrees(-6.437d)),
                                                            new Pose2d(8.918d, 0.166d, Rotation2d.fromDegrees(-44.045d)),
                                                            new Pose2d(9.95d, -1.564d, Rotation2d.fromDegrees(-54.866d)),
                                                            new Pose2d(8.452d, -3.627d, Rotation2d.fromDegrees(178.047d)),
                                                            new Pose2d(6.688d, -2.329d, Rotation2d.fromDegrees(106.165d)),
                                                            new Pose2d(8.086d, -0.233d, Rotation2d.fromDegrees(24.193d)),
                                                            new Pose2d(12.212d, 0.366d, Rotation2d.fromDegrees(11.514d)),
                                                            new Pose2d(17.47d, 2.263d, Rotation2d.fromDegrees(42.856d)),
                                                            new Pose2d(18.202d, 3.993d, Rotation2d.fromDegrees(120.964d)),
                                                            new Pose2d(15.007d, 4.426d, Rotation2d.fromDegrees(-138.18d)),
                                                            new Pose2d(14.176d, 3.128d, Rotation2d.fromDegrees(-112.549d)),
                                                            new Pose2d(16.139d, -1.364d, Rotation2d.fromDegrees(-30.018d)),
                                                            new Pose2d(18.368d, -2.762d, Rotation2d.fromDegrees(-22.203d)),
                                                            new Pose2d(22.661d, -2.329d, Rotation2d.fromDegrees(60.385d)),
                                                            new Pose2d(21.763d, 0.299d, Rotation2d.fromDegrees(150.068d)),
                                                            new Pose2d(19.1d, 0.433d, Rotation2d.fromDegrees(176.82d)),
                                                            new Pose2d(15.34d, 0.566d, Rotation2d.fromDegrees(174.181d)),
                                                            new Pose2d(9.184d, 1.897d, Rotation2d.fromDegrees(172.673d)),
                                                            new Pose2d(0.033d, 1.93d, Rotation2d.fromDegrees(-177.184d)))));

        Paths.register(new Path("Manual Slalom", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(-1.597d)),      
                                                            new Pose2d(5.857d, 5.491d, Rotation2d.fromDegrees(36.444d)),
                                                            new Pose2d(11.247d, 6.988d, Rotation2d.fromDegrees(-3.926d)),
                                                            new Pose2d(16.738d, 4.526d, Rotation2d.fromDegrees(-47.203d)),
                                                            new Pose2d(19.333d, 0.399d, Rotation2d.fromDegrees(-10.235d)),
                                                            new Pose2d(21.43d, 0.0d, Rotation2d.fromDegrees(4.824d)),
                                                            new Pose2d(23.792d, 1.065d, Rotation2d.fromDegrees(51.766d)),
                                                            new Pose2d(24.425d, 2.895d, Rotation2d.fromDegrees(85.426d)),
                                                            new Pose2d(22.228d, 6.023d, Rotation2d.fromDegrees(163.386d)),
                                                            new Pose2d(19.566d, 5.723d, Rotation2d.fromDegrees(-163.009d)),
                                                            new Pose2d(16.571d, 0.998d, Rotation2d.fromDegrees(-132.754d)),
                                                            new Pose2d(11.247d, -0.266d, Rotation2d.fromDegrees(-177.666d)),
                                                            new Pose2d(5.158d, 0.832d, Rotation2d.fromDegrees(145.739d)),
                                                            new Pose2d(0.765d, 5.391d, Rotation2d.fromDegrees(160.648d)),
                                                            new Pose2d(-2.429d, 5.79d, Rotation2d.fromDegrees(-175.986d)))));

        Paths.register(new Path("Manual Bounce1", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(3.061d, 1.93d, Rotation2d.fromDegrees(74.745d)),
                                                            new Pose2d(3.747d, 5.148d, Rotation2d.fromDegrees(79.263d)))));

        Paths.register(new Path("Manual Bounce2", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(-85.692d)),
                                                            new Pose2d(1.278d, -6.146d, Rotation2d.fromDegrees(-62.879d)),
                                                            new Pose2d(4.173d, -10.073d, Rotation2d.fromDegrees(-29.578d)),
                                                            new Pose2d(7.168d, -8.442d, Rotation2d.fromDegrees(72.474d)),
                                                            new Pose2d(7.234d, -5.314d, Rotation2d.fromDegrees(88.549d)),
                                                            new Pose2d(7.401d, -0.123d, Rotation2d.fromDegrees(78.977d)))));
    
        Paths.register(new Path("Manual Bounce3", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(-90.0d)),
                                                            new Pose2d(2.154d, -9.341d, Rotation2d.fromDegrees(-9.853d)),
                                                            new Pose2d(5.814d, -9.141d, Rotation2d.fromDegrees(22.341d)),
                                                            new Pose2d(7.412d, 0.176d, Rotation2d.fromDegrees(90.0d)))));

        Paths.register(new Path("Manual Bounce4", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(-90.119d)),
                                                            new Pose2d(1.257d, -3.186d, Rotation2d.fromDegrees(-48.538d)),
                                                            new Pose2d(4.718d, -4.85d, Rotation2d.fromDegrees(0.0d)))));



        // CONFIGURE AUTON COMMANDS
        Autonomous.register("Test Auton Driving", Paths.getPathCommand(drivetrain, "Test Path"));
        Autonomous.register("Test Straight", Paths.getPathCommand(drivetrain, "Straight"));
        Autonomous.register("Test Turn Left", Paths.getPathCommand(drivetrain, "TurnLeft"));
        Autonomous.register("Test Turn Right", Paths.getPathCommand(drivetrain, "TurnRight"));
        Autonomous.register("Test Curve Left", Paths.getPathCommand(drivetrain, "CurveLeft"));
        Autonomous.register("Test Curve Right", Paths.getPathCommand(drivetrain, "CurveRight"));
        Autonomous.register("Manual BlueA path", Paths.getPathCommand(drivetrain, "Manual BlueA"));
        Autonomous.register("Manual BlueB path", Paths.getPathCommand(drivetrain, "Manual BlueB"));
        Autonomous.register("Manual RedA path", Paths.getPathCommand(drivetrain, "Manual RedA"));
        Autonomous.register("Manual RedB path", Paths.getPathCommand(drivetrain, "Manual RedB"));
        Autonomous.register("Test PathWeaver Paths", Paths.getPathCommand(drivetrain, "Test PathWeaver"));
        Autonomous.register("Slalom", Paths.getPathCommand(drivetrain, "Slalom"));
        Autonomous.register("Manual Slalom path", Paths.getPathCommand(drivetrain, "Manual Slalom"));
        Autonomous.register("Barrel Racing", Paths.getPathCommand(drivetrain, "Barrel Racing"));
        Autonomous.register("Manual Barrel Racing", Paths.getPathCommand(drivetrain, "Manual BarrelRacing"));
        Autonomous.register("Bounce Path", new SequentialCommandGroup(
            Paths.getPathCommand(drivetrain, "Bounce 1"),
            Paths.getPathCommand(drivetrain, "Bounce 2"),
            Paths.getPathCommand(drivetrain, "Bounce 3"),
            Paths.getPathCommand(drivetrain, "Bounce 4")
        ));
        Autonomous.register("Manul Bounce Path", new SequentialCommandGroup(
            Paths.getPathCommand(drivetrain, "Manual Bounce1"),
            Paths.getPathCommand(drivetrain, "Manual Bounce2"),
            Paths.getPathCommand(drivetrain, "Manual Bounce3"),
            Paths.getPathCommand(drivetrain, "Manual Bounce4")
        ));
        Autonomous.register("Galactic Search", new GalacticSearchCommand(drivetrain, collector, indexer));
        Autonomous.register("Interstellar Accuracy", new SequentialCommandGroup(
            new InterStellarAccuracyCommand(drivetrain, collector, indexer, shooter, shooterAngle, vision, null, 
                new Path("Interstellar Green Back", greenWaypoints)),
            new InterStellarAccuracyCommand(drivetrain, collector, indexer, shooter, shooterAngle, vision, 
                new Path("Interstellar Yellow Fwr", yellowWaypoints, true), new Path("Interstellar Yellow Back", yellowWaypoints)),
            new InterStellarAccuracyCommand(drivetrain, collector, indexer, shooter, shooterAngle, vision, 
                new Path("Interstellar Blue Fwr", blueWaypoints, true), new Path("Interstellar Blue Back", blueWaypoints)),
            new InterStellarAccuracyCommand(drivetrain, collector, indexer, shooter, shooterAngle, vision, 
                new Path("Interstellar Red Fwr", redWaypoints, true), new Path("Interstellar Red Back", redWaypoints))
            )
        );
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
        // return testController.getTriggerAxis(GenericHID.Hand.kRight) - testController.getTriggerAxis(GenericHID.Hand.kLeft);
        return operator.getTriggerAxis(GenericHID.Hand.kRight) - operator.getTriggerAxis(GenericHID.Hand.kLeft);
    }

}
