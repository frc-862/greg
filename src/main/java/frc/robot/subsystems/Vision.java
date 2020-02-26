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
import frc.robot.RobotMap;

public class Vision extends SubsystemBase {
    double XValue = 0;
    double YValue = 0;
    double Found = 0;
    private final Solenoid blindedByScience;
    private final Solenoid blindedByTheLight;

    private int requestedLight = 0;
    private int actualLight = 0;
    private boolean bothRings = false;
    private boolean readyForBoth =false;
    private boolean theOneRing = false;
    private final LED led = new LED();
    /**
     * Creates a new Vision.
     */
    public Vision() {
        blindedByScience = new Solenoid(RobotMap.COMPRESSOR_ID, RobotMap.VISION_BIG_SOLENOID);
        blindedByTheLight = new Solenoid(RobotMap.COMPRESSOR_ID, RobotMap.VISION_SMALL_SOLENOID); // 21
        CommandScheduler.getInstance().registerSubsystem(this);
        DataLogger.addDataElement("XValue", () -> XValue);
        DataLogger.addDataElement("YValue", () -> YValue);
        DataLogger.addDataElement("Found", () -> Found);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        XValue = SmartDashboard.getNumber("VisionX",0);
        YValue = SmartDashboard.getNumber("VisionY",0);
        Found = SmartDashboard.getNumber("VisionFound",0);
        SmartDashboard.putNumber("X value",XValue-320);
        SmartDashboard.putNumber("found",Found);
        if(seePortTarget()){
            led.goYellow();
        }else {led.goOrangeAndBlue();}

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
            blindedByScience.set(requestedLight > 1);
            actualLight = requestedLight;
        }
    }

    public double getDistanceFromTarget() {
        return 0d;
    }

    public double getOffsetAngle() {
        return XValue-320;
    }



    public boolean seePortTarget() {
         if(Found>0){
             return true;
         }else {
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
        return 0;
    }

    public double getBestShooterVelocity() {
        return 2000;
    }

    public void ringOn(){
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
    public  void bothRingsOff(){
        blindedByTheLight.set(false);
        blindedByScience.set(false);
    }
}
