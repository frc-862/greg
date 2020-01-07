/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.lightning.util.LightningMath;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class RobotConstants {
    public final static double wheelDiameter = LightningMath.in2ft(4);
    public static final int leftCANID = 1;
    public static final int rightCANID = 4;
    public static final double openLoopRampRate = 0.2;
    public static final double closedLoopRampRate = 0.1;
    public static final int currentLimit = 40;
    public static final double maxCurrent = 50;
}
