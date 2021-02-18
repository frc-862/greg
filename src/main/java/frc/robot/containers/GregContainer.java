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
        (new Trigger((() -> operator.getTriggerAxis(GenericHID.Hand.kRight) > 0.03))).whenActive(new InstantCommand(() -> { if(!collector.isOut()) collector.extend(); }, collector));
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

        Paths.register(new Path("Manual straight", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(5.889d, 0.004d, Rotation2d.fromDegrees(0.0d)))));

        Paths.register(new Path("Manual Curve Right", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(5.67d, -3.264d, Rotation2d.fromDegrees(-82.171d)))));

        Paths.register(new Path("Manual Curve Left", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.64d)),
                                                            new Pose2d(3.574d, 3.711d, Rotation2d.fromDegrees(79.38d)))));

        Paths.register(new Path("Manual Turn Right", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(5.5d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(7.5d, -3.5d, Rotation2d.fromDegrees(-90.0d)))));

        Paths.register(new Path("Manual Turn Left", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(6.325d, 0.0d, Rotation2d.fromDegrees(0.876d)),
                                                            new Pose2d(7.476d, 2.5d, Rotation2d.fromDegrees(90.0d)))));

        Paths.register(new Path("Manual Blue A", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(13.866d, 1.329d, Rotation2d.fromDegrees(42.306d)),
                                                            new Pose2d(14.499d, 8.683d, Rotation2d.fromDegrees(8.746d)),
                                                            new Pose2d(21.317d, 6.259d, Rotation2d.fromDegrees(-14.524d)),
                                                            new Pose2d(27.952d, 5.109d, Rotation2d.fromDegrees(0.0d)))));
        
        Paths.register(new Path("Manual Blue B", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(12.469d, 1.51d, Rotation2d.fromDegrees(28.824d)),
                                                            new Pose2d(18.592d, 7.499d, Rotation2d.fromDegrees(-12.51d)),
                                                            new Pose2d(23.217d, 2.907d, Rotation2d.fromDegrees(-36.236d)),
                                                            new Pose2d(27.819d, 0.32d, Rotation2d.fromDegrees(-26.565d)))));

        Paths.register(new Path("Manual Red A", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(6.41d, -0.035d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(11.541d, -2.468d, Rotation2d.fromDegrees(4.53d)),
                                                            new Pose2d(14.0d, 5.195d, Rotation2d.fromDegrees(50.548d)),
                                                            new Pose2d(27.598d, 5.185d, Rotation2d.fromDegrees(-4.399d)))));

        Paths.register(new Path("Manual Red B", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(5.611d, -2.993d, Rotation2d.fromDegrees(-41.987d)),
                                                            new Pose2d(10.639d, -8.842d, Rotation2d.fromDegrees(0.507d)),
                                                            new Pose2d(15.863d, -3.817d, Rotation2d.fromDegrees(25.712d)),
                                                            new Pose2d(27.289d, -5.268d, Rotation2d.fromDegrees(0.0d)))));

        Paths.register(new Path("Manual BarrelRacing", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(7.425d, 0.503d, Rotation2d.fromDegrees(-5.248d)),   
                                                            new Pose2d(10.611d, -0.01d, Rotation2d.fromDegrees(-31.45d)),  
                                                            new Pose2d(12.295d, -1.897d, Rotation2d.fromDegrees(-62.835d)),
                                                            new Pose2d(10.347d, -4.718d, Rotation2d.fromDegrees(178.047d)),
                                                            new Pose2d(8.054d, -3.764d, Rotation2d.fromDegrees(127.857d)), 
                                                            new Pose2d(9.17d, -0.497d, Rotation2d.fromDegrees(26.694d)),   
                                                            new Pose2d(13.948d, -0.229d, Rotation2d.fromDegrees(11.514d)), 
                                                            new Pose2d(20.108d, 0.903d, Rotation2d.fromDegrees(42.856d)),  
                                                            new Pose2d(19.702d, 4.353d, Rotation2d.fromDegrees(145.196d)),
                                                            new Pose2d(16.272d, 5.124d, Rotation2d.fromDegrees(-154.772d)),
                                                            new Pose2d(14.71d, 2.344d, Rotation2d.fromDegrees(-81.529d)),
                                                            new Pose2d(17.348d, -1.938d, Rotation2d.fromDegrees(-37.488d)),
                                                            new Pose2d(20.676d, -4.048d, Rotation2d.fromDegrees(-22.203d)),
                                                            new Pose2d(25.607d, -2.79d, Rotation2d.fromDegrees(60.385d)),
                                                            new Pose2d(24.552d, 0.436d, Rotation2d.fromDegrees(167.376d)),
                                                            new Pose2d(21.061d, 0.639d, Rotation2d.fromDegrees(176.82d)),
                                                            new Pose2d(16.252d, 0.172d, Rotation2d.fromDegrees(-178.794d)),
                                                            new Pose2d(9.15d, 0.822d, Rotation2d.fromDegrees(177.51d)),
                                                            new Pose2d(0.546d, 0.7d, Rotation2d.fromDegrees(-177.184d)))));

        Paths.register(new Path("Manual Slalom", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(7.625d, 5.137d, Rotation2d.fromDegrees(36.444d)),  
                                                            new Pose2d(13.016d, 6.634d, Rotation2d.fromDegrees(-3.926d)), 
                                                            new Pose2d(18.506d, 4.172d, Rotation2d.fromDegrees(-47.203d)),
                                                            new Pose2d(21.102d, 0.046d, Rotation2d.fromDegrees(-10.235d)),
                                                            new Pose2d(23.198d, -0.354d, Rotation2d.fromDegrees(4.824d)), 
                                                            new Pose2d(25.561d, 0.711d, Rotation2d.fromDegrees(51.766d)), 
                                                            new Pose2d(26.193d, 2.541d, Rotation2d.fromDegrees(85.426d)), 
                                                            new Pose2d(23.997d, 5.669d, Rotation2d.fromDegrees(163.386d)),
                                                            new Pose2d(21.335d, 5.37d, Rotation2d.fromDegrees(-163.009d)),
                                                            new Pose2d(18.34d, 0.645d, Rotation2d.fromDegrees(-132.754d)),
                                                            new Pose2d(13.016d, -0.62d, Rotation2d.fromDegrees(-177.666d)),
                                                            new Pose2d(6.926d, 0.478d, Rotation2d.fromDegrees(145.739d)),
                                                            new Pose2d(2.534d, 5.037d, Rotation2d.fromDegrees(160.648d)),
                                                            new Pose2d(-0.661d, 5.436d, Rotation2d.fromDegrees(-175.986d)))));

        Paths.register(new Path("Manual Bounce1", Arrays.asList(new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
                                                            new Pose2d(3.935d, 1.268d, Rotation2d.fromDegrees(52.306d)),
                                                            new Pose2d(5.116d, 5.185d, Rotation2d.fromDegrees(79.263d)))));

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
        Autonomous.register("Manual Curve Right Path", Paths.getPathCommand(drivetrain, "Manual Curve Right"));
        Autonomous.register("Manual Curve left Path", Paths.getPathCommand(drivetrain, "Manual Curve Left"));
        Autonomous.register("Manual Turn Right Path", Paths.getPathCommand(drivetrain, "Manual Turn Right"));
        Autonomous.register("Manual Turn Left Path", Paths.getPathCommand(drivetrain, "Manual Turn Left"));
        Autonomous.register("Manual Straight Path", Paths.getPathCommand(drivetrain, "Manual Straight"));
        Autonomous.register("Manual BlueA path", Paths.getPathCommand(drivetrain, "Manual BlueA"));
        Autonomous.register("Manual BlueB path", Paths.getPathCommand(drivetrain, "Manual BlueB"));
        Autonomous.register("Manual RedA path", Paths.getPathCommand(drivetrain, "Manual RedA"));
        Autonomous.register("Manual RedB path", Paths.getPathCommand(drivetrain, "Manual RedB"));
        Autonomous.register("Test PathWeaver Paths", Paths.getPathCommand(drivetrain, "Test PathWeaver"));
        Autonomous.register("Slalom", Paths.getPathCommand(drivetrain, "Slalom"));
        Autonomous.register("Manual Slalom Path", Paths.getPathCommand(drivetrain, "Manual Slalom"));
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
