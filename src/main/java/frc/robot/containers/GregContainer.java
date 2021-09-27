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
import frc.robot.commands.drivetrain.VoltDrive;
import frc.robot.commands.auto.GalacticSearchCommand;
import frc.robot.commands.auto.GalacticSearchIdentifier;
import frc.lightning.subsystems.*;
import frc.lightning.subsystems.IMU;
import frc.robot.JoystickConstants;
import frc.robot.auto.AutonGenerator;
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

    // AUTON GENERATOR
    private static final AutonGenerator autonGenerator = new AutonGenerator(drivetrain, collector, indexer, shooter, leadScrew, vision);

    public GregContainer() { super(); }

    @Override
    protected void configureButtonBindings() {

        // DRIVER
        (new JoystickButton(driverLeft, 1)).whileHeld(new VisionRotate(drivetrain,vision));
        (new JoystickButton(driverRight, 1)).whileHeld(new FullAutoFireOne(drivetrain, vision, shooter, leadScrew, indexer, true));
        (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()-> shooter.stop(), shooter));
        (new JoystickButton(driverRight, 1)).whenReleased(new InstantCommand(()-> vision.ringOff(), vision));
        (new JoystickButton(driverLeft, 1)).whenReleased(new InstantCommand(()-> vision.ringOff(), vision));

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
        (new JoystickButton(climberController, JoystickConstants.A)).whileHeld(climber::up, climber);
        (new JoystickButton(climberController, JoystickConstants.B)).whileHeld(climber::down, climber);

    }

    @Override
    protected void configureSystemTests() {

    }

    @Override
    protected void configureDefaultCommands() {

        drivetrain.setDefaultCommand(new VoltDrive(drivetrain, () -> -driverLeft.getY(), () -> -driverRight.getY()));
        indexer.setDefaultCommand(new IndexerCommand(indexer));
        collector.setDefaultCommand(new Collect(collector, this::getCollectPower));
        climber.setDefaultCommand(new ManualClimb(climber, () -> -climberController.getRawAxis(1), () -> -climberController.getRawAxis(5)));

        // FOR TESTING
        // leadScrew.setDefaultCommand(new RunCommand(() -> leadScrew.setPower(-operator.getY(GenericHID.Hand.kLeft)), leadScrew));

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

        // final var path_config_tab = Shuffleboard.getTab("Path Config");
        // path_config_tab.add("Path Config Command", new PathConfigCommand(drivetrain, () -> -driverLeft.getY(), () -> -driverRight.getY()));

    }

    @Override
    protected void configureAutonomousCommands() {

        // FOR SKILLS CHALLENGE
        boolean isSkillsChallenge = false;
        if(isSkillsChallenge) {
            skillsChallengeAutoConfig();
        }

        // FOR COMPETITION
        var autoCmds = autonGenerator.getCommands();
        for(var key : autoCmds.keySet()) {
            var cmd = autoCmds.get(key);
            Autonomous.register(key, cmd);
        }

    }

    @Override
    public LightningConfig getConfig() { return config; }

    @Override
    public LightningDrivetrain getDrivetrain() { return drivetrain; }

    public double getCollectPower() { 
        return operator.getTriggerAxis(GenericHID.Hand.kRight) - operator.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    private void skillsChallengeAutoConfig() {

        // Galactic Search
        Autonomous.register("Galactic Search", new GalacticSearchCommand(drivetrain, collector, indexer));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("A-RED", "ared.waypoints"));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("A-BLUE", "ablue.waypoints"));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("B-RED", "bred.waypoints"));
        Paths.register(PathUtils.pathFromDeployedWaypointFile("B-BLUE", "bblue.waypoints"));
        Paths.register(new Path("NONE", Arrays.asList(new Pose2d(0d, 0d, Rotation2d.fromDegrees(0d)), new Pose2d(1d, 0d, Rotation2d.fromDegrees(0d)))));

        // AutoNav
        Autonomous.register("Slalom", PathUtils.pathFromDeployedWaypointFile("Slalom", "slalom.waypoints").getCommand(drivetrain));
        Autonomous.register("Barrel Racing", PathUtils.pathFromDeployedWaypointFile("Barrle Racing", "barrel.waypoints").getCommand(drivetrain)); 
        double bounceWaitDuration = 0.01d; 
        Autonomous.register("Bounce", new SequentialCommandGroup(
            PathUtils.pathFromDeployedWaypointFile("Bounce 1", "bounce1.waypoints").getCommand(drivetrain),
            new WaitCommand(bounceWaitDuration),
            PathUtils.subsequentPathFromDeployedWaypointFile("Bounce 2", "bounce2.waypoints", true).getCommand(drivetrain),
            new WaitCommand(bounceWaitDuration),
            PathUtils.subsequentPathFromDeployedWaypointFile("Bounce 3", "bounce3.waypoints").getCommand(drivetrain),
            new WaitCommand(bounceWaitDuration),
            PathUtils.subsequentPathFromDeployedWaypointFile("Bounce 4", "bounce4.waypoints", true).getCommand(drivetrain)
        ));

    }

}
