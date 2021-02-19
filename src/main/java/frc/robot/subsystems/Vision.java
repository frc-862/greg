/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.InterpolatedMap;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class Vision extends SubsystemBase {

    public static final int IMG_CNTR_COL = 320;
    
    public static final int DEFAULT_SHOOTER_ANGLE = 20;
    public static final int DEFAULT_SHOOTER_VELOCITY = 1000;
    public static final int DEFAULT_SHOOTER_BACKSPIN = 500;

    public static final double VERT_BIAS_STEP = 0.5;
    public static final double HORIZ_BIAS_STEP = 2d;

    private double XValue = 0d;
    private double YValue = 0d;
    private double Found  = 0d;
    private double Height = 0d;

    private final Solenoid innerLEDRing;
    private final Solenoid outerLEDRing;

    private double startVertBias = 0;
    private double startHorizBias = -28.; //14.;

    private double verticalBias = startVertBias;
    private double horizontalBias = startHorizBias;
    
    private NetworkTable table;

    private InterpolatedMap shooterAngleInterpolationTable  = new InterpolatedMap();
    private InterpolatedMap flywheelSpeedInterpolationTable = new InterpolatedMap();
    private InterpolatedMap backspinInterpolationTable      = new InterpolatedMap();
    
    public Vision() {

        CommandScheduler.getInstance().registerSubsystem(this);

        configShooterSpeed();
        configShooterBackspin();
        configShooterAngle();

        innerLEDRing = new Solenoid(RobotMap.COMPRESSOR_ID, RobotMap.VISION_BIG_SOLENOID);
        outerLEDRing = new Solenoid(RobotMap.COMPRESSOR_ID, RobotMap.VISION_SMALL_SOLENOID);
        
        DataLogger.addDataElement("XValue", () -> XValue);
        DataLogger.addDataElement("YValue", () -> YValue);
        DataLogger.addDataElement("Found", () -> Found);
        DataLogger.addDataElement("VisionHeight", () -> Height);

        table = NetworkTableInstance.getDefault().getTable("Vision");

        var tab = Shuffleboard.getTab("Vision");
        tab.addNumber("Best Shooter Angle",    this::getBestShooterAngle);
        tab.addNumber("Best Shooter Backspin", this::getBestShooterBackspin);
        tab.addNumber("Best Shooter Speed",    this::getBestShooterVelocity);
        tab.addNumber("Image Offset Center Pixel", this::getOffsetAngle);

        tab.addNumber("BiasVert", () -> verticalBias);
        tab.addNumber("BiasHoriz", () -> horizontalBias);

    }

    @Override
    public void periodic() {
        XValue = table.getEntry("VisionX").getDouble(0);
        YValue = table.getEntry("VisionY").getDouble(0);
        Height = table.getEntry("VisionHeight").getDouble(0); 
        Found  = table.getEntry("VisionFound").getDouble(0);
    }

    public double getOffsetAngle() {
        return ((XValue - IMG_CNTR_COL) + horizontalBias);
    }

    public boolean seePortTarget() {
        return (Found > 0d);
    }

    public double getBestShooterAngle() {
        if (Height > 0) {
            return shooterAngleInterpolationTable.get(Height) + verticalBias;
        } else {
            return DEFAULT_SHOOTER_ANGLE;
        }
    }

    public double getBestShooterVelocity() {
        if (Height > 0) {
            return flywheelSpeedInterpolationTable.get(Height);
        } else {
            return DEFAULT_SHOOTER_VELOCITY;
        }
    }

    public double getBestShooterBackspin() {
        if (Height > 0) {
            return backspinInterpolationTable.get(Height);
        } else {
            return DEFAULT_SHOOTER_BACKSPIN;
        }
    }

    public void ringOn(){
        outerLEDRing.set(false);
        innerLEDRing.set(true);
    }

    public void ringOff(){
        outerLEDRing.set(false);
        innerLEDRing.set(false);
    }

    public void bothRingsOn(){
        outerLEDRing.set(true);
        innerLEDRing.set(true);
    }
    
    public void biasUp() {
        verticalBias += VERT_BIAS_STEP;
    }

    public void biasDown() {
        verticalBias -= VERT_BIAS_STEP;
    }

    public void biasLeft() {
        horizontalBias -= HORIZ_BIAS_STEP;
    }

    public void biasRight() {
        horizontalBias += HORIZ_BIAS_STEP;
    }

    public void biasReset() {
        verticalBias = startVertBias;
        horizontalBias = startHorizBias;
    }

    // Interpolation Table Data Configuration

    private void configShooterAngle(){
        // input in target height pixels TODO verify this is correct interpretation
        // output degrees
        shooterAngleInterpolationTable.put(115.0, 33.75); // 46.5); // closet shot-2
        shooterAngleInterpolationTable.put(95.0,  29.75); // 35.0); // 10ft-2
        shooterAngleInterpolationTable.put(62.0,  27.5); // 26.5); // close trench-2
        shooterAngleInterpolationTable.put(39.0,  26.5); // 23.0);
    }
    
    private void configShooterSpeed() {
        // input in target height pixels TODO verify this is correct interpretation
        // output RPM
        flywheelSpeedInterpolationTable.put(115.0, 3250.0);
        flywheelSpeedInterpolationTable.put(95.0,  3500.0);
        flywheelSpeedInterpolationTable.put(62.0,  3500.0);
        flywheelSpeedInterpolationTable.put(39.0,  3500.0);
    }

    private void configShooterBackspin() {
        // input in target height pixels TODO verify this is correct interpretation
        // output RPM
        backspinInterpolationTable.put(115.0, 750.0); // closest shot
        backspinInterpolationTable.put(95.0,  750.0); // 10ft
        backspinInterpolationTable.put(62.0,  1000.0); // close trench
        backspinInterpolationTable.put(39.0,  1000.0);
    }

}
