package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.InterpolatedMap;

public class ShooterAngle extends SubsystemBase {
    InterpolatedMap shooterAngle = new InterpolatedMap();
    public void ShooterAngle(){
        shooterAngle.put(10.0,200.0);
        shooterAngle.put(18.0,300.0);
        shooterAngle.put(35.0,250.0);
        shooterAngle.put(35.0,300.0);
    }
    public double getDesiredAngle(double distance){
        return shooterAngle.get(distance);
    }
    public void setDesiredAngle(double distance) {

    }
    public double getAngle() {
        return 0;
    }

}
