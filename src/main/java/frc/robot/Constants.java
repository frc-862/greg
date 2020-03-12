package frc.robot;

import frc.lightning.util.RamseteGains;
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
    public static final double TICS_PER_ROTATION = 2048; // 4 * 360;
    public static final double NEO_TICKS_PER_REV = 42; 
    public static final int NEO_MAX_RPM = 5700;

    // DRIVETRAIN
    public static final RamseteGains QUASAR = new RamseteGains(0.74, // trackWidth 
                                                                0.141, // kS
                                                                3.55, // kV
                                                                0.421, // kA
                                                                0.119, // left_kP
                                                                0d, // left_kI
                                                                0d, // left_kD
                                                                0.119, // right_kP
                                                                0d, // right_kI
                                                                0d, // right_kD
                                                                5d, // maxVelocity (ft/sec)
                                                                5d); // maxAcceleration (ft/sec^2)

    public static final RamseteGains GREG = new RamseteGains(0.68284937, // trackWidth 
                                                                0.172, // kS
                                                                2.54, // kV - 254 . . . these numbers must work!
                                                                0.44, // kA
                                                                0.0353, // left_kP
                                                                0d, // left_kI
                                                                0d, // left_kD
                                                                0.0353, // right_kP
                                                                0d, // right_kI
                                                                0d, // right_kD  
                                                                5.4d, // maxVelocity (ft/sec)
                                                                5d); // maxAcceleration (ft/sec^2)

    public static final double VOLT_LIMIT = 12d;

    //DRIVE
    public static final double SETTLE_TIME = 5.0;
    public static final double MOVING_CURRENT = 40;
    public static final double MOVING_VELOCITY = 40;
    public static final double FLYWHEEL_EPSILON = 100;
    public static final double ROTATION_TOLERANCE = 75;
    public static final double VISION_ROTATION_TOLERANCE = 15;

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
    public static REVGains Motor1Gains = new REVGains(.00026, 0.000000004, 0.0, 0.000175, 0.0, 1.0, -1.0, NEO_MAX_RPM);
    public static REVGains Motor2Gains = new REVGains(.00026, 0.000000004, 0.0, 0.000175, 0.0, 1.0, -1.0, NEO_MAX_RPM);
    public static REVGains Motor3Gains = new REVGains(.00026, 0.000000004, 0.0, 0.000175, 0.0, 1.0, -1.0, NEO_MAX_RPM);
    public static final double shooterAngleTolerance = 5.0;

    //PROTOTYPE SHOOTER
    public static double PShooterKp = 1000 / 5000; // (1200/5000);
    public static double PShooterKi = 0d; // 0.0000000001;
    public static double PShooterKd = 0d; // 0.000001;
    public static double M1ShooterKf = 0.0002350;
    public static double M2shooterKf = 0.00024250;
    public static double M3shooterKf = 0.0002350;

    //Shooter Angle
    public static int kTimeoutMs = 100;
    public static int kPIDLoopIdx = 0;
    public static double kAdjusterF = 0;
    public static double kAdjusterD = 0;
    public static double kAdjusterI = 0;
    public static double kAdjusterP = 0.013333333333;
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


