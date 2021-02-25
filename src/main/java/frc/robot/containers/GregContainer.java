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

    }

    @Override
    protected void configureAutonomousCommands() {

        //CONFIGURE PATHS
        Paths.register(new Path("Test Path", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                            new Pose2d(1d, 1d, Rotation2d.fromDegrees(90d)))));

        Paths.register(new Path("Circle", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                                            new Pose2d(1d, 1d, Rotation2d.fromDegrees(90d)),
                                                            new Pose2d(0d, 2d, Rotation2d.fromDegrees(-180d)),
                                                            new Pose2d(-1d, 1d, Rotation2d.fromDegrees(-90d)),
                                                            new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)))));

        Paths.register(new Path("Slalom", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                                        new Pose2d(1.1d, 0.2d, Rotation2d.fromDegrees(33.5d)),
                                                        new Pose2d(2d, 1.1d, Rotation2d.fromDegrees(40d)),
                                                        new Pose2d(4.25d, 2d, Rotation2d.fromDegrees(0d)),
                                                        new Pose2d(6.06d, 1.75d, Rotation2d.fromDegrees(-45d)),
                                                        new Pose2d(6.55d, 0.71d, Rotation2d.fromDegrees(-63.7d)),
                                                        new Pose2d(7.75d, 0d, Rotation2d.fromDegrees(0d)),
                                                        new Pose2d(8.44d, 0.67d, Rotation2d.fromDegrees(90d)),
                                                        new Pose2d(7.66d, 1.45d, Rotation2d.fromDegrees(180d)),
                                                        new Pose2d(6.95d, 0.92d, Rotation2d.fromDegrees(240d)),
                                                        new Pose2d(5.7d, -0.14d, Rotation2d.fromDegrees(190d)),
                                                        new Pose2d(3.14d, -0.08d, Rotation2d.fromDegrees(153d)),
                                                        new Pose2d(2.35d, 0.53d, Rotation2d.fromDegrees(132d)),
                                                        new Pose2d(0.61d, 1.58d, Rotation2d.fromDegrees(170d)),
                                                        new Pose2d(0d, 1.5d, Rotation2d.fromDegrees(180d)))));

                    
        Paths.register(new Path("Barrel Racing", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), 
                                                                new Pose2d(3.26d, 0d, Rotation2d.fromDegrees(0d)),
                                                                new Pose2d(4.45d, -.51, Rotation2d.fromDegrees(-60d)),
                                                                new Pose2d(3.85d, -1.57d, Rotation2d.fromDegrees(180d)),
                                                                new Pose2d(3.1d, -1.05d, Rotation2d.fromDegrees(-250d)),
                                                                new Pose2d(3.9d, 0d, Rotation2d.fromDegrees(0d)),
                                                                new Pose2d(6.11d, -.14d, Rotation2d.fromDegrees(4d)),
                                                                new Pose2d(6.73d, .95d, Rotation2d.fromDegrees(94d)),
                                                                new Pose2d(5.83d, 1.76d, Rotation2d.fromDegrees(-175d)),
                                                                new Pose2d(5.15d, .93d, Rotation2d.fromDegrees(-80d)),
                                                                new Pose2d(6.92d, -1.06d, Rotation2d.fromDegrees(-32d)),
                                                                new Pose2d(8.33d, -.86d, Rotation2d.fromDegrees(60d)),
                                                                new Pose2d(7.6d, 0.117d, Rotation2d.fromDegrees(180d)),
                                                                new Pose2d(4.16d, .24d, Rotation2d.fromDegrees(-176d)),
                                                                new Pose2d(0d, 0d, Rotation2d.fromDegrees(-180d)))));

        Paths.register(new Path("Bounce", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)),
                                                            new Pose2d(.977, 0.1779d, Rotation2d.fromDegrees(46.8d)),
                                                            new Pose2d(1.29d, 1.6d, Rotation2d.fromDegrees(90d)),
                                                            new Pose2d(1.29d, 1.6d, Rotation2d.fromDegrees(-90d)),
                                                            new Pose2d(1.6d, .6d, Rotation2d.fromDegrees(-64d)),
                                                            new Pose2d(2.25d, -.64d, Rotation2d.fromDegrees(-60d)),
                                                            new Pose2d(3.19d, -1.39d, Rotation2d.fromDegrees(23d)),
                                                            new Pose2d(3.699d, -.62d, Rotation2d.fromDegrees(84.4d)),
                                                            new Pose2d(3.7d, 1.395d, Rotation2d.fromDegrees(90d)),
                                                            new Pose2d(3.7d, 1.395d, Rotation2d.fromDegrees(-90d)),
                                                            new Pose2d(4.25d, -.5d, Rotation2d.fromDegrees(-78d)),
                                                            new Pose2d(4.86d, -1.5d, Rotation2d.fromDegrees(0d)),
                                                            new Pose2d(6.175d, -.71d, Rotation2d.fromDegrees(83d)),
                                                            new Pose2d(6.38d, 1.389d, Rotation2d.fromDegrees(90d)),
                                                            new Pose2d(6.38d, 1.389d, Rotation2d.fromDegrees(-90d)),
                                                            new Pose2d(6.53d, .38d, Rotation2d.fromDegrees(-38d)),
                                                            new Pose2d(8.2d, -.369d, Rotation2d.fromDegrees(-22.5d)))));
                                                        
        // CONFIGURE AUTON COMMANDS
        Autonomous.register("TEST - Circle", Paths.getPathCommand(drivetrain, "Circle"));
        Autonomous.register("TEST - Straight", Paths.getPathCommand(drivetrain, "Test Path"));
        Autonomous.register("Slalom", Paths.getPathCommand(drivetrain, "Slalom"));
        Autonomous.register("Barrel Racing", Paths.getPathCommand(drivetrain, "Barrel Racing"));
        Autonomous.register("Bounce Path", Paths.getPathCommand(drivetrain, "Bounce"));
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
        // return testController.getTriggerAxis(GenericHID.Hand.kRight) - testController.getTriggerAxis(GenericHID.Hand.kLeft);
        return operator.getTriggerAxis(GenericHID.Hand.kRight) - operator.getTriggerAxis(GenericHID.Hand.kLeft);
    }

}
