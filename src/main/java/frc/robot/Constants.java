package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.misc.REVGains;

public class Constants {

    // DEVELOPMENT
    public static final boolean TUNING_ENABLED = true;
    public static final boolean DRIVETRAIN_LOGGING_ENABLED = true;
    public static final boolean DRIVETRAIN_DASHBOARD_ENABLED = true;
    public static final boolean CORE_LOGGING_ENABLED = true;
    public static final boolean CORE_DASHBOARD_ENABLED = true;
    public static final boolean HYPERION_LOGGING_ENABLED = true;
    public static final boolean HYPERION_DASHBOARD_ENABLED = true;

    // HARDWARE
    public static final double TICS_PER_ROTATION = 4 * 360;
    public static final int NEO_MAX_RPM = 5676;

    // DRIVETRAIN
    public static final double OPEN_LOOP_RAMP_RATE = 0.5;
    public static final double CLOSE_LOOP_RAMP_RATE = 0.5;
    public static final double SETTLE_TIME = 5.0;
    public static final double MOVING_CURRENT = 40;
    public static final double MOVING_VELOCITY = 40;
    public static REVGains leftGains = new REVGains(4e-3, 1e-6,  0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains rightGains = new REVGains(4e-3, 1e-6,  0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains quasarLeftGains = new REVGains(4e-3, 1e-6,  0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains quasarRightGains = new REVGains(4e-3, 1e-6,  0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM

    // HYPERION
    public static REVGains hyperionGains = new REVGains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static final double MAX_TURRET_POWER = 0.33d;
    public static final double FWD_LIMIT_DEG = 151.0d;
    public static final double REV_LIMIT_DEG = -151.0d;
    public static final double FWD_LIMIT_TICKS = FWD_LIMIT_DEG / (360 / 76.55);
    public static final double REV_LIMIT_TICKS = REV_LIMIT_DEG / (360 / 76.55);
}
