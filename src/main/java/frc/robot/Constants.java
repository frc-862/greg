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
    public static final double NEO_TICKS_PER_REV = 4096;
    public static final int NEO_MAX_RPM = 5700;

    // DRIVETRAIN
    public static final double kS = 0.01; // 0.113;
    public static final double kV = 0d; // 0.0132;
    public static final double kA = 0d; // 0.00156;
    public static final double left_kP = 0.01;// 0.0716;
    public static final double left_kI = 0d;
    public static final double left_kD = 0d;
    public static final double right_kP = 0.01;// 0.0716;
    public static final double right_kI = 0d;
    public static final double right_kD = 0d;

    public static final double VOLT_LIMIT = 12d;

    public static final double gregTrackWidth = 0d;
    public static final double quasarTrackWidth = 186d;

    //DRIVE
    public static final double OPEN_LOOP_RAMP_RATE = 0.5;
    public static final double CLOSE_LOOP_RAMP_RATE = 0.5;
    public static final double SETTLE_TIME = 5.0;
    public static final double MOVING_CURRENT = 40;
    public static final double MOVING_VELOCITY = 40;
    public static REVGains leftGains = new REVGains(4e-3, 1e-6,  0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains rightGains = new REVGains(4e-3, 1e-6,  0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains quasarLeftGains = new REVGains(0.00007, 0.0,  0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains quasarRightGains = new REVGains(0.00007, 0.0,  0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM

    // HYPERION
    public static REVGains hyperionGains = new REVGains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static final double MAX_TURRET_POWER = 0.33d;
    public static final double FWD_LIMIT_DEG = 151.0d;
    public static final double REV_LIMIT_DEG = -151.0d;
    public static final double FWD_LIMIT_TICKS = FWD_LIMIT_DEG / (360 / 76.55);
    public static final double REV_LIMIT_TICKS = REV_LIMIT_DEG / (360 / 76.55);
}
