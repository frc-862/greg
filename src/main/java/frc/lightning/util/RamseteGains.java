/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.util;

import edu.wpi.first.wpilibj.util.Units;

/**
 * Add your docs here.
 */
public class RamseteGains {

    private double trackWidth;

    private double kS;
    private double kV;
    private double kA;

    private double left_kP;
    private double left_kI;
    private double left_kD;
    
    private double right_kP;
    private double right_kI;
    private double right_kD;

    private double maxVelocity;
    private double maxAcceleration;

    public RamseteGains (double trackWidth, 
                            double kS,
                            double kV,
                            double kA,
                            double left_kP,
                            double left_kI,
                            double left_kD,
                            double right_kP,
                            double right_kI,
                            double right_kD,
                            double maxVelocity,
                            double maxAcceleration) {
        setTrackWidth(trackWidth);
        setkS(kS);
        setkV(kV);
        setkA(kA);
        setLeft_kP(left_kP);
        setLeft_kI(left_kI);
        setLeft_kD(left_kD);
        setRight_kP(right_kP);
        setRight_kI(right_kI);
        setRight_kD(right_kD);
        setMaxVelocity(maxVelocity);
        setMaxAcceleration(maxAcceleration);
    }
    
    public double getMaxVelocity() { return maxVelocity; }

    public void setMaxVelocity(double maxVelocity) { this.maxVelocity = Units.feetToMeters(maxVelocity); }

    public double getMaxAcceleration() { return maxAcceleration; }

    public void setMaxAcceleration(double maxAcceleration) { this.maxAcceleration = Units.feetToMeters(maxAcceleration); }

    public double getTrackWidth() { return trackWidth; }

    public void setTrackWidth(double trackWidth) { this.trackWidth = trackWidth; }

    public double getkS() { return kS; }

    public void setkS(double kS) { this.kS = kS; }

    public double getkV() { return kV; }

    public void setkV(double kV) { this.kV = kV; }

    public double getkA() { return kA; }

    public void setkA(double kA) { this.kA = kA; }

    public double getLeft_kP() { return left_kP; }

    public void setLeft_kP(double left_kP) { this.left_kP = left_kP; }

    public double getLeft_kI() { return left_kI; }

    public void setLeft_kI(double left_kI) { this.left_kI = left_kI; }

    public double getLeft_kD() { return left_kD; }

    public void setLeft_kD(double left_kD) { this.left_kD = left_kD; }

    public double getRight_kP() { return right_kP; }

    public void setRight_kP(double right_kP) { this.right_kP = right_kP; }

    public double getRight_kI() { return right_kI; }

    public void setRight_kI(double right_kI) { this.right_kI = right_kI; }

    public double getRight_kD() { return right_kD; }

    public void setRight_kD(double right_kD) { this.right_kD = right_kD; }

}
