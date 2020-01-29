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
    public static final int NEO_MAX_RPM = 5700;

    // DRIVETRAIN
    public static final double OPEN_LOOP_RAMP_RATE = 0.5;
    public static final double CLOSE_LOOP_RAMP_RATE = 0.5;
    public static final double SETTLE_TIME = 5.0;
    public static final double MOVING_CURRENT = 40;
    public static final double MOVING_VELOCITY = 40;
    public static final double FLYWHEEL_EPSILON = 100;
    public static final double ROTATION_TOLERANCE = 3;

    public static REVGains leftGains = new REVGains(4e-3, 1e-6, 0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains rightGains = new REVGains(4e-3, 1e-6, 0.0, 0.0, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains quasarLeftGains = new REVGains(0.00007, 0.0, 0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains quasarRightGains = new REVGains(0.00007, 0.0, 0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM

    // HYPERION
    public static REVGains hyperionGains = new REVGains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static final double MAX_TURRET_POWER = 0.33d;
    public static final double FWD_LIMIT_DEG = 151.0d;
    public static final double REV_LIMIT_DEG = -151.0d;
    public static final double FWD_LIMIT_TICKS = FWD_LIMIT_DEG / (360 / 76.55);
    public static final double REV_LIMIT_TICKS = REV_LIMIT_DEG / (360 / 76.55);

    // SHOOTER
    public static REVGains Motor1Gains = new REVGains(0.00007, 0.0, 0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM);
    public static REVGains Motor2Gains = new REVGains(0.00007, 0.0, 0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM);
    public static REVGains Motor3Gains = new REVGains(0.00007, 0.0, 0.0, 0.00018, 0.0, 1.0, -1.0, NEO_MAX_RPM);
    public static final double shooterAngleTolerance = 1;

    //PROTOTYPE SHOOTER

    public static final double PShooterKp = .07 / 5000;//(1200/5000);
    public static final double PShooterKi = 0.0000085;
    public static final double PShooterKd =0.;
    public static final double M1ShooterKf = 0.00020992727273;
    public static final double M2shooterKf=0.0002080;
    public static final double M3shooterKf=0.0002215727273;
}
//    public static double M1ShooterKf = 0.0002150;
//    public static double M2shooterKf=0.0002220;
//    public static double M3shooterKf=0.0002250;
//    public static double PShooterKp = .1 / 4500;
//    public static double M1ShooterKf = 1/4500;
//    public static double M2shooterKf = 1/4500;
//    public static double M3shooterKf = 1/4500;
//
//}


