package frc.lightning;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import frc.lightning.auto.Autonomous;
import frc.lightning.fault.FaultCode;
import frc.lightning.fault.FaultMonitor;
import frc.lightning.fault.TimedFaultMonitor;
import frc.lightning.fault.FaultCode.Codes;
import frc.lightning.logging.DataLogger;
import frc.lightning.testing.SystemTestCommand;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Base robot class, provides
 * {@link frc.lightning.logging.DataLogger logging},
 * {@link frc.lightning..fault.FaultMonitor fault monitoring}, and loops with varying
 * periods {@link LightningRobot#robotBackgroundPeriodic() background},
 * {@link LightningRobot#robotLowPriorityPeriodic() low}, and
 * {@link LightningRobot#robotMediumPriorityPeriodic() medium} priority
 * loops.
 *
 * Uses {@link frc.lightning.auto.Autonomous} to configure autonomous commands. Also includes
 * self-testing support with {@link frc.lightning.testing.SystemTestCommand} (Still in progress).
 */
public class LightningRobot extends TimedRobot {

    private LightningContainer container;

    private final static double SETTLE_TIME = 3.0;

    public DataLogger dataLogger = DataLogger.getLogger();

    private int counter = 0;

    private int medPriorityFreq = (int) Math.round(0.1 / getPeriod());

    private double loopTime;

    private int lowPriorityFreq = (int) Math.round(1 / getPeriod());

    private int backgroundPriorityFreq = (int) Math.round(10 / getPeriod());

    private Command autonomousCommand;

    public LightningRobot(LightningContainer container) {
        this.container = container;
    }

    /**
     * Getter for the configured robot container.
     * @return the {@link frc.lightning.LightningContainer} for the robot.
     */
    public LightningContainer getContainer() {
        return container;
    }

    /**
     * Nothing should happen here.
     */
    @Override
    public void disabledPeriodic() {}

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     *
     * If you override it, be sure to call super.robotInit
     */
    @Override
    public void robotInit() {
        System.out.println("LightningRobot.robotInit");

        // No Live Window for now
        LiveWindow.disableAllTelemetry();

        // Note our start time
        System.out.println("Starting time: " + Timer.getFPGATimestamp());

        // Read our version properties
        try {
            Properties props = new Properties();
            var stream = ClassLoader.getSystemResourceAsStream("version.properties");
            if (stream != null) {
                props.load(stream);
                System.out.println("Version: " + props.getProperty("VERSION_NAME", "n/a"));
                System.out.println("Build: " + props.getProperty("VERSION_BUILD", "n/a"));
                System.out.println("Built at: " + props.getProperty("BUILD_TIME", "n/a"));
                System.out.println("Git branch: " + props.getProperty("GIT_BRANCH", "n/a"));
                System.out.println("Git hash: " + props.getProperty("GIT_HASH", "n/a"));
                System.out.println("Git status: " + props.getProperty("BUILD_STATUS", "n/a"));
            }
        } catch (IOException e) {
            System.out.println("Unable to read build version information.");
        }

        // By this point all datalog fields should be registered
        DataLogger.preventNewDataElements();

        // Set up a fault monitor for our loop time
        FaultMonitor.register(new TimedFaultMonitor(Codes.SLOW_LOOPER, () -> getLoopTime() > getPeriod(),
                              0.08, "Loop is running slow: " + getLoopTime()));

        // Put our fault codes on the dashboard
        FaultCode.eachCode((code, state) -> {
            var nte = Shuffleboard.getTab("Fault Codes")
            .add("FAULT_" + code.toString(), state)
            .withWidget("Boolean Box")
            .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "maroon"))
            .getEntry();
            FaultCode.setNetworkTableEntry(code, nte);
        });

        // Load our autonomous chooser
        Autonomous.load();

    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and Shuffleboard integrated updating.
     *
     * If you override this method, be sure to call super.robotPeriod() as
     * it drives our lower priority loops, datalogging, fault monitoring,
     * etc.
     */
    @Override
    public void robotPeriodic() {
        double time = Timer.getFPGATimestamp();
        if (time > SETTLE_TIME) {
            counter += 1;
            if (counter % medPriorityFreq == 0) {
                robotMediumPriorityPeriodic();
            }
            if (counter % lowPriorityFreq == 0) {
                robotLowPriorityPeriodic();
            }
            if (counter % backgroundPriorityFreq == 0) {
                robotBackgroundPeriodic();
            }

            FaultMonitor.checkMonitors();
            DataLogger.logData();
            loopTime = Timer.getFPGATimestamp() - time;
        }

        CommandScheduler.getInstance().run();
    }

    /**
     * A slower loop, running once every 10 seconds
     *
     * Note as currently implemented it still needs to
     * complete in our loop time or it delay higher
     * priority opterations. If you have a low priority,
     * long running operation, consider creating a background
     * thread.
     */
    protected void robotBackgroundPeriodic() {
        DataLogger.flush();
        DataLogger.checkBaseFileName();
    }

    /**
     *  A slow loop, running once a second
     *
     * Note as currently implemented it still needs to
     * complete in our loop time or it delay higher
     * priority opterations. If you have a low priority,
     * long running operation, consider creating a background
     * thread.
     */
    protected void robotLowPriorityPeriodic() {
        DataLogger.getLogger().getLogWriter().drain();
    }

    /**
     *  A loop, running 10 times a second
     *
     * Note as currently implemented it still needs to
     * complete in our loop time or it delay higher
     * priority opterations. If you have a low priority,
     * long running operation, consider creating a background
     * thread.
     */
    protected void robotMediumPriorityPeriodic() {
        FaultCode.update();
    }

    /**
     * Getter for robot loop time.
     */
    private double getLoopTime() {
        return loopTime;
    }

    /**
     * The default implementation handles getting the selected command
     * from Shuffleboard.
     *
     * TODO consider adding check for failure to communicate with Shuffleboard
     * and using the default command.
     *
     * If you override this method, be sure to call {@code super.autonomousInit()} or
     * the selected registered command will not be executed.
     */
    @Override
    public void autonomousInit() {
        System.out.println("LightningRobot.autonomousInit");
        autonomousCommand = Autonomous.getAutonomous();
        if (autonomousCommand != null) autonomousCommand.schedule();
    }

    /**
     * The default implementation handles canceling the autonomous command.
     *
     * If you override this method, be sure to call {@code super.teleopInit()} or
     * the autonomous command will not be canceled when teleop starts.
     * 
     * Alternatively, if you want the autonomous command to finish running 
     * into teleop, you may override this method w/o calling {@code super.teleopInit()}
     */
    @Override
    public void teleopInit() {
        System.out.println("LightningRobot.teleopInit");
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    private static SystemTestCommand stc = new SystemTestCommand();

    private boolean systemTestFlag = false;

    /**
     * The default implementation cancles all commands and releases the default commands
     * from the subsystems, and schedules a {@link frc.lightning.testing.SystemTestCommand}.
     *
     * It is reccomended that you avoid overriding this method.
     */
    @Override
    public void testInit() {
        System.out.println("LightningRobot.testInit");
        getContainer().releaseDefaultCommands();
        CommandScheduler.getInstance().cancelAll();
        stc.initialize();
    }

    @Override
    public void testPeriodic() {
        if(!stc.isFinished()) {
            stc.execute();
        } else if(stc.isFinished() && !systemTestFlag) {
            stc.end(false);
            systemTestFlag = true;
            //this.disabledInit();
        }
    }

    /**
     * The default implementation configures the default commands in the event they have
     * been disabled by {@link LightningRobot#testInit()}.
     */
    @Override
    public void disabledInit() {
        System.out.println("LightningRobot.disabledInit");
        getContainer().configureDefaultCommands();
    }

}
