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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.GenericHID;
import frc.lightning.LightningConfig;
import frc.lightning.LightningContainer;
import frc.lightning.auto.Autonomous;
import frc.lightning.auto.*;
import frc.lightning.commands.RumbleCommand;
import frc.lightning.commands.VoltDrive;
import frc.robot.commands.auto.DrivetrainCharacterization;
import frc.robot.commands.auto.GalacticSearchCommand;
import frc.robot.commands.auto.GalacticSearchIdentifier;
import frc.robot.commands.auto.PathConfigCommand;
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
import frc.robot.misc.PathUtils;
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
    private static final LeadScrew leadScrew = new LeadScrew();
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

    public GregContainer() {
        super();
    }

    @Override
    protected void configureButtonBindings() {

        // DRIVER
        (new JoystickButton(driverLeft, 1)).whileHeld(new VisionRotate(drivetrain,vision));
        (new JoystickButton(driverRight, 1)).whileHeld(new FullAutoFireOne(drivetrain,vision,shooter,leadScrew,indexer,true));
        // (new JoystickButton(driverRight, 1)).whenPressed(new FullAutoFireMagazine(drivetrain, vision, shooter, leadScrew, indexer));
        (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()-> shooter.stop(), shooter));
        (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()-> vision.ringOff(), vision));

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
        // //leadScrew.setDefaultCommand(new RunCommand(() -> leadScrew.setPower(-operator.getY(GenericHID.Hand.kLeft)), leadScrew));
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

        // LEAD SCREW DASHBOARD
        // final var flyWheelAngle = Shuffleboard.getTab("Shooter").add("SetAngle", 100)
        //         .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 80, "max", 155)).getEntry();
        // flyWheelAngle.addListener((n) -> {
        //     leadScrew.setAngle(flyWheelAngle.getDouble(100)); // setDesiredAngle?
        // }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        // GENERAL DASHBOARD COMMANDS
        final var vision_tab = Shuffleboard.getTab("Vision");
        vision_tab.add("Vision Rotate", new VisionRotate(drivetrain, vision));
        vision_tab.add("Ring 1 on", new InstantCommand(vision::ringOn));
        vision_tab.add("Ring 2 on", new InstantCommand(vision::bothRingsOn));
        vision_tab.add("Ring off", new InstantCommand(vision::ringOff));
        vision_tab.add("Galactic Search Inference", new GalacticSearchIdentifier());

        final var indexer_tab = Shuffleboard.getTab("Indexer");
        indexer_tab.add("safety in", new InstantCommand(indexer::safteyClosed));
        indexer_tab.add("safety out", new InstantCommand(indexer::safteyOpen));
        indexer_tab.add("zero balls held", new InstantCommand(indexer::resetBallCount));
        indexer_tab.add("collect",  new CollectEject(collector, () -> operator.getTriggerAxis(GenericHID.Hand.kRight), () -> operator.getTriggerAxis(GenericHID.Hand.kLeft)));
        indexer_tab.add("Fire 3", new FireThree(shooter, indexer, leadScrew, vision, collector));

        final var pose_tab = Shuffleboard.getTab("Pose");
        pose_tab.add("ResetPose", new InstantCommand(drivetrain::resetSensorVals, drivetrain));

        final var shooter_tab = Shuffleboard.getTab("Shooter");
        shooter_tab.add("Manual lead screw", new RunCommand(() -> leadScrew.setPower(-operator.getY(GenericHID.Hand.kLeft)), leadScrew));
        shooter_tab.add("Fire 3", new FireThree(shooter, indexer, leadScrew, vision, collector));

        final var path_config_tab = Shuffleboard.getTab("Path Config");
        path_config_tab.add("Path Config Command", new PathConfigCommand(drivetrain, () -> -driverLeft.getY(), () -> -driverRight.getY()));

    }

    @Override
    protected void configureAutonomousCommands() {

        //CONFIGURE PATHS
        // Paths.register(new Path("Test Path", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
        //     new Pose2d(1d, 1d, Rotation2d.fromDegrees(90d))
        // )));

        // Paths.register(new Path("Orange Line", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
        //     new Pose2d(5.8d, 0d, Rotation2d.fromDegrees(0d))
        // )));

        // Paths.register(new Path("Circle", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(1d, 1d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(0d, 2d, Rotation2d.fromDegrees(-180d)),
        //     new Pose2d(-1d, 1d, Rotation2d.fromDegrees(-90d)),
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d))
        // )));

        // Paths.register(new Path("Other Circle", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(1d, -1d, Rotation2d.fromDegrees(-90d)),
        //     new Pose2d(0d, -2d, Rotation2d.fromDegrees(180d)),
        //     new Pose2d(-1d, -1d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d))
        // )));

        // Paths.register(new Path("Base Slalom", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(1.1d, 0.2d, Rotation2d.fromDegrees(33.5d)),
        //     new Pose2d(2d, 1.1d, Rotation2d.fromDegrees(40d)),
        //     new Pose2d(4.25d, 2d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(6.06d, 1.75d, Rotation2d.fromDegrees(-45d)),
        //     new Pose2d(6.55d, 0.71d, Rotation2d.fromDegrees(-63.7d)),
        //     new Pose2d(7.75d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(8.44d, 0.67d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(7.66d, 1.45d, Rotation2d.fromDegrees(180d)),
        //     new Pose2d(6.95d, 0.92d, Rotation2d.fromDegrees(240d)),
        //     new Pose2d(5.7d, -0.14d, Rotation2d.fromDegrees(190d)),
        //     new Pose2d(3.14d, -0.08d, Rotation2d.fromDegrees(153d)),
        //     new Pose2d(2.35d, 0.53d, Rotation2d.fromDegrees(132d)),
        //     new Pose2d(0.61d, 1.58d, Rotation2d.fromDegrees(170d)),
        //     new Pose2d(0d, 1.5d, Rotation2d.fromDegrees(180d))
        // )));

                    
        // Paths.register(new Path("Base Barrel Racing", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
        //     new Pose2d(3.26d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(4.45d, -.51, Rotation2d.fromDegrees(-60d)),
        //     new Pose2d(3.85d, -1.57d, Rotation2d.fromDegrees(180d)),
        //     new Pose2d(3.1d, -1.05d, Rotation2d.fromDegrees(-250d)),
        //     new Pose2d(3.9d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(6.11d, -.14d, Rotation2d.fromDegrees(4d)),
        //     new Pose2d(6.73d, .95d, Rotation2d.fromDegrees(94d)),
        //     new Pose2d(5.83d, 1.76d, Rotation2d.fromDegrees(-175d)),
        //     new Pose2d(5.15d, .93d, Rotation2d.fromDegrees(-80d)),
        //     new Pose2d(6.92d, -1.06d, Rotation2d.fromDegrees(-32d)),
        //     new Pose2d(8.33d, -.86d, Rotation2d.fromDegrees(60d)),
        //     new Pose2d(7.6d, 0.117d, Rotation2d.fromDegrees(180d)),
        //     new Pose2d(4.16d, .24d, Rotation2d.fromDegrees(-176d)),
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(-180d))
        // )));

        // Paths.register(new Path("Base Bounce", Arrays.asList(
        //     new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(.977, 0.1779d, Rotation2d.fromDegrees(46.8d)),
        //     new Pose2d(1.29d, 1.6d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(1.29d, 1.6d, Rotation2d.fromDegrees(-90d)),
        //     new Pose2d(1.6d, .6d, Rotation2d.fromDegrees(-64d)),
        //     new Pose2d(2.25d, -.64d, Rotation2d.fromDegrees(-60d)),
        //     new Pose2d(3.19d, -1.39d, Rotation2d.fromDegrees(23d)),
        //     new Pose2d(3.699d, -.62d, Rotation2d.fromDegrees(84.4d)),
        //     new Pose2d(3.7d, 1.395d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(3.7d, 1.395d, Rotation2d.fromDegrees(-90d)),
        //     new Pose2d(4.25d, -.5d, Rotation2d.fromDegrees(-78d)),
        //     new Pose2d(4.86d, -1.5d, Rotation2d.fromDegrees(0d)),
        //     new Pose2d(6.175d, -.71d, Rotation2d.fromDegrees(83d)),
        //     new Pose2d(6.38d, 1.389d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(6.38d, 1.389d, Rotation2d.fromDegrees(-90d)),
        //     new Pose2d(6.53d, .38d, Rotation2d.fromDegrees(-38d)),
        //     new Pose2d(8.2d, -.369d, Rotation2d.fromDegrees(-22.5d))
        // )));

        // Paths.register(new Path("Manual Slalom", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(2.253d, 1.448d, Rotation2d.fromDegrees(11.447d)),
        //     new Pose2d(3.839d, 1.655d, Rotation2d.fromDegrees(-3.926d)),
        //     new Pose2d(5.641d, 1.272d, Rotation2d.fromDegrees(-47.207d)),
        //     new Pose2d(6.432d, 0.014d, Rotation2d.fromDegrees(-42.349d)),
        //     new Pose2d(7.172d, -0.138d, Rotation2d.fromDegrees(14.797d)),
        //     new Pose2d(7.791d, 0.217d, Rotation2d.fromDegrees(51.782d)),
        //     new Pose2d(7.984d, 0.775d, Rotation2d.fromDegrees(85.417d)),
        //     new Pose2d(7.47d, 1.603d, Rotation2d.fromDegrees(158.579d)),
        //     new Pose2d(6.712d, 1.548d, Rotation2d.fromDegrees(-162.993d)),
        //     new Pose2d(5.59d, 0.196d, Rotation2d.fromDegrees(-149.105d)),
        //     new Pose2d(3.923d, -0.092d, Rotation2d.fromDegrees(-177.678d)),
        //     new Pose2d(2.111d, 0.146d, Rotation2d.fromDegrees(145.728d)),
        //     new Pose2d(0.772d, 1.535d, Rotation2d.fromDegrees(160.635d)),
        //     new Pose2d(-0.201d, 1.657d, Rotation2d.fromDegrees(-175.943d))
        // )));

        // Paths.register(new Path("Manual Barrel Racing", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(2.263d, 0.153d, Rotation2d.fromDegrees(-1.695d)),
        //     new Pose2d(3.234d, -0.003d, Rotation2d.fromDegrees(-31.446d)),
        //     new Pose2d(3.748d, -0.578d, Rotation2d.fromDegrees(-62.853d)),
        //     new Pose2d(3.154d, -1.438d, Rotation2d.fromDegrees(178.068d)),
        //     new Pose2d(2.455d, -1.147d, Rotation2d.fromDegrees(127.886d)),
        //     new Pose2d(2.795d, -0.152d, Rotation2d.fromDegrees(26.67d)),
        //     new Pose2d(4.251d, -0.07d, Rotation2d.fromDegrees(11.551d)),
        //     new Pose2d(6.129d, 0.275d, Rotation2d.fromDegrees(42.857d)),
        //     new Pose2d(6.005d, 1.327d, Rotation2d.fromDegrees(145.152d)),
        //     new Pose2d(4.96d, 1.562d, Rotation2d.fromDegrees(-154.769d)),
        //     new Pose2d(4.484d, 0.714d, Rotation2d.fromDegrees(-81.539d)),
        //     new Pose2d(5.288d, -0.591d, Rotation2d.fromDegrees(-37.512d)),
        //     new Pose2d(6.302d, -1.234d, Rotation2d.fromDegrees(-22.218d)),
        //     new Pose2d(7.805d, -0.85d, Rotation2d.fromDegrees(60.39d)),
        //     new Pose2d(6.42d, 0.195d, Rotation2d.fromDegrees(176.866d)),
        //     new Pose2d(4.954d, 0.053d, Rotation2d.fromDegrees(-178.817d)),
        //     new Pose2d(2.789d, 0.25d, Rotation2d.fromDegrees(177.523d)),
        //     new Pose2d(0.166d, 0.213d, Rotation2d.fromDegrees(-177.214d))
        // )));

        // Paths.register(new Path("Bounce 1", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(1.199d, 0.387d, Rotation2d.fromDegrees(52.286d)),
        //     new Pose2d(1.559d, 1.58d, Rotation2d.fromDegrees(90d))
        // )));

        // Paths.register(new SubsequentPath("Bounce 2", Arrays.asList(
        //     new Pose2d(1.559d, 1.58d, Rotation2d.fromDegrees(90d)),
        //     new Pose2d(1.949d, -0.293d, Rotation2d.fromDegrees(62.873d)),
        //     new Pose2d(2.831d, -1.49d, Rotation2d.fromDegrees(29.564d)),
        //     new Pose2d(3.744d, -0.993d, Rotation2d.fromDegrees(-72.497d)),
        //     new Pose2d(3.764d, -0.039d, Rotation2d.fromDegrees(-88.576d)),
        //     new Pose2d(3.815d, 1.543d, Rotation2d.fromDegrees(-90d))
        // ), true));

        // Paths.register(new SubsequentPath("Bounce 3", Arrays.asList(
        //     new Pose2d(3.815d, 1.543d, Rotation2d.fromDegrees(-90d)),
        //     new Pose2d(4.495d, -1.348d, Rotation2d.fromDegrees(-9.845d)),
        //     new Pose2d(5.61d, -1.287d, Rotation2d.fromDegrees(22.333d)),
        //     new Pose2d(6.097d, 1.553d, Rotation2d.fromDegrees(90.0d))
        // )));

        // Paths.register(new SubsequentPath("Bounce 4", Arrays.asList(
        //     new Pose2d(6.097d, 1.553d, Rotation2d.fromDegrees(90.0d)),
        //     new Pose2d(6.513d, 0.447d, Rotation2d.fromDegrees(48.54d)),
        //     new Pose2d(7.568d, -0.06d, Rotation2d.fromDegrees(0.0d))
        // ), true));

        // Paths.register(new Path("A-BLUE", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(4.226d, 0.024d, Rotation2d.fromDegrees(42.302d)),
        //     new Pose2d(4.419d, 2.266d, Rotation2d.fromDegrees(8.757d)),
        //     new Pose2d(6.497d, 1.527d, Rotation2d.fromDegrees(-14.537d)),
        //     new Pose2d(8.52d, 1.176d, Rotation2d.fromDegrees(0.0d))
        // )));

        // Paths.register(new Path("B-BLUE", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(3.801d, 0.46d, Rotation2d.fromDegrees(28.818d)),
        //     new Pose2d(5.667d, 2.286d, Rotation2d.fromDegrees(-12.518d)),
        //     new Pose2d(7.077d, 0.886d, Rotation2d.fromDegrees(-36.233d)),
        //     new Pose2d(8.479d, 0.098d, Rotation2d.fromDegrees(-26.6d))
        // )));

        // Paths.register(new Path("A-RED", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(1.954d, -0.011d, Rotation2d.fromDegrees(-29.603d)),
        //     new Pose2d(3.518d, -0.752d, Rotation2d.fromDegrees(4.521d)),
        //     new Pose2d(4.267d, 1.583d, Rotation2d.fromDegrees(50.566d)),
        //     new Pose2d(8.412d, 1.58d, Rotation2d.fromDegrees(-4.399d))
        // )));

        // Paths.register(new Path("B-RED", Arrays.asList(
        //     new Pose2d(0.0d, 0.0d, Rotation2d.fromDegrees(0.0d)),
        //     new Pose2d(1.71d, -0.912d, Rotation2d.fromDegrees(-41.97d)),
        //     new Pose2d(3.243d, -2.695d, Rotation2d.fromDegrees(0.493d)),
        //     new Pose2d(4.835d, -1.163d, Rotation2d.fromDegrees(25.703d)),
        //     new Pose2d(8.318d, -1.606d, Rotation2d.fromDegrees(0.0d))
        // )));
        
        Paths.register(PathUtils.pathFromDeployedWaypointFile("New Circle", "circle.waypoints"));

        Paths.register(PathUtils.pathFromDeployedWaypointFile("A-RED", "ared.waypoints"));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("A-BLUE", "ablue.waypoints"));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("B-RED", "bred.waypoints"));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("B-BLUE", "bblue.waypoints"));
        Paths.register(new Path("NONE", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), new Pose2d(1d, 0d, Rotation2d.fromDegrees(0d)))));


        // CONFIGURE AUTON COMMANDS

        // AutoNav
        Autonomous.register("Slalom", PathUtils.pathFromDeployedWaypointFile("Slalom", "slalom.waypoints").getCommand(drivetrain));
        Autonomous.register("Barrel Racing", PathUtils.pathFromDeployedWaypointFile("Barrle Racing", "barrel.waypoints").getCommand(drivetrain)); 
        double bounceWaitDuration = 0.1d; // TODO 0?
        Autonomous.register("Bounce", new SequentialCommandGroup(
            PathUtils.pathFromDeployedWaypointFile("Bounce 1", "bounce1.waypoints").getCommand(drivetrain),
            new WaitCommand(bounceWaitDuration),
            PathUtils.subsequentPathFromDeployedWaypointFile("Bounce 2", "bounce2.waypoints", true).getCommand(drivetrain),
            new WaitCommand(bounceWaitDuration),
            PathUtils.subsequentPathFromDeployedWaypointFile("Bounce 3", "bounce3.waypoints").getCommand(drivetrain),
            new WaitCommand(bounceWaitDuration),
            PathUtils.subsequentPathFromDeployedWaypointFile("Bounce 4", "bounce4.waypoints", true).getCommand(drivetrain)
        ));
        
        // Galactic Search
        Autonomous.register("Galactic Search", new GalacticSearchCommand(drivetrain, collector, indexer));
        
        // Testing/Tuning
        Autonomous.register("Drivetrain Characterization", new DrivetrainCharacterization(drivetrain));
        Autonomous.register("TEST - New Circle", Paths.getPathCommand(drivetrain, "New Circle"));
        // Autonomous.register("TEST - Circle", Paths.getPathCommand(drivetrain, "Circle"));
        // Autonomous.register("TEST - Other Circle", Paths.getPathCommand(drivetrain, "Other Circle"));
        // Autonomous.register("TEST - Straight", Paths.getPathCommand(drivetrain, "Test Path"));
        // Autonomous.register("TEST - To Orange Line", Paths.getPathCommand(drivetrain, "Orange Line"));
        // Autonomous.register("BASE - Slalom", Paths.getPathCommand(drivetrain, "Base Slalom"));
        // Autonomous.register("BASE - Barrel Racing", Paths.getPathCommand(drivetrain, "Base Barrel Racing"));
        // Autonomous.register("BASE - Bounce Path", Paths.getPathCommand(drivetrain, "Base Bounce"));
        // Autonomous.register("Manual Slalom", Paths.getPathCommand(drivetrain, "Manual Slalom"));
        // Autonomous.register("Manual Barrel Racing", Paths.getPathCommand(drivetrain, "Manual Barrel Racing"));

        // double bounceWaitDuration = 0.1d; // TODO 0?
        // Autonomous.register("Manual Slalom Bounce", new SequentialCommandGroup(
        //     Paths.getPathCommand(drivetrain, "Bounce 1"),
        //     new WaitCommand(bounceWaitDuration),
        //     Paths.getPathCommand(drivetrain, "Bounce 2"),
        //     new WaitCommand(bounceWaitDuration),
        //     Paths.getPathCommand(drivetrain, "Bounce 3"),
        //     new WaitCommand(bounceWaitDuration),
        //     Paths.getPathCommand(drivetrain, "Bounce 4")
        // ));

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
