/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class RobotMap {

    // DRIVETRAIN
    public static final int MOTORS_PER_SIDE = 3;
    public static final int LEFT_1_CAN_ID   = 1; // MASTER
    public static final int LEFT_2_CAN_ID   = 2;
    public static final int LEFT_3_CAN_ID   = 3;
    public static final int RIGHT_1_CAN_ID  = 4; // MASTER
    public static final int RIGHT_2_CAN_ID  = 5;
    public static final int RIGHT_3_CAN_ID  = 6;
    public static final int LEFT_ENCODER_CHANNEL_A = 0;
    public static final int LEFT_ENCODER_CHANNEL_B = 1;
    public static final int RIGHT_ENCODER_CHANNEL_A = 2;
    public static final int RIGHT_ENCODER_CHANNEL_B = 3;

    // SHOOTER
    public static final int SHOOTER_1 = 7; // TOP LEFT
    public static final int SHOOTER_2 = 8; // BOTTOM
    public static final int SHOOTER_3 = 9; // TOP RIGHT
    public static final int SHOOTER_ANGLE = 13;

    // CORE
    public static final int COMPRESSOR_ID = 20;
    public static final int PIGEON_ID = 8;

    // COLLECTOR
    public static final int LONGITUDNAL_MOTOR = 10; // LEFT SIDE
    public static final int LINEAR_MOTOR = 11; // RIGHT SIDE
    public static final int COLLECTOR_OUT_CHANNEL = 2;
    public static final int COLLECTOR_IN_CHANNEL = 5;

    // INDEXER
    public static final int INDEXER = 12;
    public static final int SAFTEY_IN_CHANNEL = 1;
    public static final int SAFTEY_OUT_CHANNEL = 6;
    public static final int COLLECT_SENSOR = 4;

    // CONTROL PANEL
    public static final int CONTROL_PANEL = 16;

    // CLIMBER
    public static final int CLIMBER_LEFT = 14;
    public static final int CLIMBER_RIGHT = 15;

    public static final int VISION_SMALL_SOLENOID = 3;
    public static final int VISION_BIG_SOLENOID = 4;
    public static final int EJECT_SENSOR = 5;
}
