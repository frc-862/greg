/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.InterpolatedMap;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard; 
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class Vision extends SubsystemBase {
    double XValue = 0;
    double YValue = 0;
    double Found = 0;
    double Height = 0;
    private final Solenoid blindedByScience;
    private final Solenoid blindedByTheLight;

    private int requestedLight = 0;
    private int actualLight = 0;
    private boolean bothRings = false;
    private boolean readyForBoth =false;
    private boolean theOneRing = false;

    private double startVertBias= 0;
    private double startHorizBias=14.;

    private double verticalBias=startVertBias;
    private double horizontalBias=startHorizBias;
    NetworkTable table ;
    InterpolatedMap shooterAngle = new InterpolatedMap();
    InterpolatedMap flyWheelSpeed = new InterpolatedMap();
    InterpolatedMap backspinData = new InterpolatedMap();
    /**
     * Creates a new Vision.
     */
    public Vision() {
        configShooterSpeed();
        configShooterBackspin();
        shooterAngleConfig();
        blindedByScience = new Solenoid(RobotMap.COMPRESSOR_ID, RobotMap.VISION_BIG_SOLENOID);
        blindedByTheLight = new Solenoid(RobotMap.COMPRESSOR_ID, RobotMap.VISION_SMALL_SOLENOID); // 21
        CommandScheduler.getInstance().registerSubsystem(this);
        DataLogger.addDataElement("XValue", () -> XValue);
        DataLogger.addDataElement("YValue", () -> YValue);
        DataLogger.addDataElement("Found", () -> Found);
        DataLogger.addDataElement("VisionHeight", () -> Height);
        table = NetworkTableInstance.getDefault().getTable("Vision");
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        XValue = table.getEntry("VisionX").getDouble(0);  //SmartDashboard.getNumber("VisionX",0);
        YValue = table.getEntry("VisionY").getDouble(0); //SmartDashboard.getNumber("VisionY",0);
        Height = table.getEntry("VisionHeight").getDouble(0); //SmartDashboard.Number("VisionHeight",0);
        SmartDashboard.putNumber("Best Shooter Angle",getBestShooterAngle());
        SmartDashboard.putNumber("Best Shooter backspin",getBestShooterBackspin());
        SmartDashboard.putNumber("Best Shooter speed",getBestShooterVelocity());
        Found = table.getEntry("VisionFound").getDouble(0); //SmartDashboard.getNumber("VisionFound",0);
        SmartDashboard.putNumber("X value",XValue-320);
        SmartDashboard.putNumber("found",Found);

//        if(seePortTarget()){
//            led.goSolidGreen();
//        }else {led.goOrangeAndBlue();}

        if (requestedLight > actualLight) {
            if (actualLight == 0) {
                blindedByScience.set(true);
                actualLight += 1;
            } else {
                blindedByTheLight.set(true);
                actualLight += 1;
            }
        } else if (requestedLight < actualLight) {
            blindedByScience.set(requestedLight > 0);
            blindedByTheLight.set(requestedLight > 1);
            actualLight = requestedLight;
        }

        SmartDashboard.putNumber("BiasVert", verticalBias);
        SmartDashboard.putNumber("BiasHoriz", horizontalBias);

    }

    public double getDistanceFromTarget() {
        return 0d;
    }

    public double getOffsetAngle() {
        return XValue - 320 + horizontalBias;
    }

    public boolean seePortTarget() {
         if (Found > 0) {
             return true;
         } else {
             return false;
         }
    }

    public static double getTargetFlywheelSpeed() {
        return 0d;
    }

    public boolean seeBayTarget() {
        return false;
    }

    public double getBestShooterAngle() {
        if (Height > 0) {
            return shooterAngle.get(Height) + verticalBias;
        } else {
            return 20;
        }
    }

    public double getBestShooterVelocity() {
        if (Height > 0) {
            return flyWheelSpeed.get(Height);
        } else {
            return 1000;
        }
    }

    public double getBestShooterBackspin() {
        if (Height > 0) {
        return backspinData.get(Height);
        } else {
            return 500;
        }
    }

    public void ringOn(){
        blindedByTheLight.set(false);
        blindedByScience.set(true);
    }

    public void   ringOff(){
        blindedByTheLight.set(false);
        blindedByScience.set(false);
    }

    public  void bothRingsOn(){
        blindedByTheLight.set(true);
        blindedByScience.set(true);
    }

    //data sheet

    private void shooterAngleConfig(){
        //left input      right outputb    256

        shooterAngle.put(115.0, 46.5);//closet shot-2
        shooterAngle.put(95.0, 35.0);//10ft-2
        shooterAngle.put(62.0, 26.5);//close trench-2
        shooterAngle.put(39.0,23.0);

//        shooterAngle.put(45.0, 200.0);
    }
    private void configShooterSpeed() {
        //left input      right output
        flyWheelSpeed.put(115.0,3250.0);
        flyWheelSpeed.put(95.0,3500.0);
        flyWheelSpeed.put(62.0,3500.0);
        flyWheelSpeed.put(39.0,3500.0);
//        flyWheelSpeed.put(45.0,3500.0);
    }
    private void configShooterBackspin() {
        //left input      right output
        backspinData.put(115.0,750.0);//closest shot
        backspinData.put(95.0,750.0);//10ft
        backspinData.put(62.0,1000.0);//close trench
        backspinData.put(39.0,1000.0);
//        backspinData.put(45.0,1500.0);

    }

    public void biasUp() {
        verticalBias += 0.5;
    }

    public void biasDown() {
        verticalBias -= 0.5;
    }

    public void biasLeft() {
        horizontalBias -= 2;
    }

    public void biasRight() {
        horizontalBias += 2;
    }

    public void biasReset() {
        verticalBias = startVertBias;
        horizontalBias = startHorizBias;
    }
}
