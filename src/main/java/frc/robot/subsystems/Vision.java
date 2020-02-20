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
import frc.robot.subsystems.leds.LED;

public class Vision extends SubsystemBase {
    double XValue=0;
    double YValue=0;
    double Found =0;
    private final Solenoid blindedByTheLight;
    private final LED led = new LED();
    /**
     * Creates a new Vision.
     */
    public Vision() {
        blindedByTheLight =new Solenoid(21,3);
        CommandScheduler.getInstance().registerSubsystem(this);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        XValue = SmartDashboard.getNumber("VisionX",0);
        YValue = SmartDashboard.getNumber("VisionY",0);
        Found = SmartDashboard.getNumber("VisionFound",0);
        DataLogger.addDataElement("XValue",()->XValue);
        DataLogger.addDataElement("YValue",()->YValue);
        DataLogger.addDataElement("Found",()->Found);
        SmartDashboard.putNumber("X value",XValue);
        SmartDashboard.putNumber("found",Found);
        if(seePortTarget()){
            //led.goYellow();
        }else {/*led.goOrangeAndBlue();*/}
    }

    public double getDistanceFromTarget() {
        return 0d;
    }

    public double getOffsetAngle() {
        return XValue;
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
        return 0;
    }

    public void  ringOn(){
        blindedByTheLight.set(true);
    }

    public void  ringOff(){
        blindedByTheLight.set(false);
    }
}
