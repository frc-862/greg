/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.misc;

import com.revrobotics.CANPIDController;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants;

/**
 * Used to set the PID gains on various REV hardware components.
 * These gains can be loaded into any CAN REV PID Controller i.e. {@link com.revrobotics.CANPIDController}
 */
public class REVGains {

    private double kP,
            kI,
            kD,
            kFF,
            kIz,
            kMaxOutput,
            kMinOutput,
            maxRPM;

    /**
     * Creates a brand new set of PID gains which are all initialized to zero
     */
    public REVGains() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Creates a new set of PID gains initialized to the given values
     * @param kP Initial P Gain
     * @param kI Initial I Gain
     * @param kD Initial D Gain
     * @param kFF Initial Feed Forward Gain
     * @param kIz Initial I Zone Gain
     * @param kMaxOutput Initial Maximum Output
     * @param kMinOutput Initial Minimum Output
     * @param maxRPM Maximum revolutions per minute of motor
     */
    public REVGains (double kP, double kI, double kD, double kFF, double kIz, double kMaxOutput, double kMinOutput, double maxRPM) {
        this.setkP(kP);
        this.setkI(kI);
        this.setkD(kD);
        this.setkFF(kFF);
        this.setkIz(kIz);
        this.setkMaxOutput(kMaxOutput);
        this.setkMinOutput(kMinOutput);
        this.setMaxRPM(maxRPM);
    }

    /**
     * Puts a set of gains to be edited on {@link edu.wpi.first.wpilibj.smartdashboard.SmartDashboard}
     * @param name Name of gains to be tuned (must match the name of the gains when they are retrieved)
     * @param gains The gains to be tuned
     */
    public static void putGainsToBeTunedOnDash(String name, REVGains gains, CANPIDController controller) {
        if(Constants.TUNING_ENABLED) {
            final var tab = Shuffleboard.getTab("PID Tuning");

            tab.add(name + " P Gain", gains.getkP())
               .getEntry().addListener((n) -> {
                            final var v = n.getEntry().getDouble(gains.getkP());
                            controller.setP(v);
                            gains.setkP(v);
                            },
               EntryListenerFlags.kUpdate);

            tab.add(name + " I Gain", gains.getkI())
               .getEntry().addListener((n) -> {
                        final var v = n.getEntry().getDouble(gains.getkI());
                        controller.setI(v);
                        gains.setkI(v);
                    },
               EntryListenerFlags.kUpdate);

            tab.add(name + " D Gain", gains.getkD())
               .getEntry().addListener((n) -> {
                        final var v = n.getEntry().getDouble(gains.getkD());
                        controller.setD(v);
                        gains.setkD(v);
                    },
               EntryListenerFlags.kUpdate);

            tab.add(name + " I Zone", gains.getkIz())
               .getEntry().addListener((n) -> {
                        final var v = n.getEntry().getDouble(gains.getkIz());
                        controller.setI(v);
                        gains.setkI(v);
                    },
               EntryListenerFlags.kUpdate);

            tab.add(name + " Feed Forward", gains.getkFF())
               .getEntry().addListener((n) -> {
                        final var v = n.getEntry().getDouble(gains.getkFF());
                        controller.setFF(v);
                        gains.setkFF(v);
                    },
               EntryListenerFlags.kUpdate);

            tab.add(name + " Max Output", gains.getkMaxOutput())
               .getEntry().addListener((n) -> {
                        gains.setkMaxOutput(n.getEntry().getDouble(gains.getkMaxOutput()));
                        controller.setOutputRange(gains.getkMinOutput(), gains.getkMaxOutput());
                    },
               EntryListenerFlags.kUpdate);

            tab.add(name + " Min Output", gains.getkMinOutput())
               .getEntry().addListener((n) -> {
                        gains.setkMinOutput(n.getEntry().getDouble(gains.getkMinOutput()));
                        controller.setOutputRange(gains.getkMinOutput(), gains.getkMaxOutput());
                    },
               EntryListenerFlags.kUpdate);
        }
    }

    /**
     * @return the maxRPM
     */
    public double getMaxRPM() {
        return maxRPM;
    }

    /**
     * @param maxRPM the maxRPM to set
     */
    public void setMaxRPM(double maxRPM) {
        this.maxRPM = maxRPM;
    }

    /**
     * @return the kMinOutput
     */
    public double getkMinOutput() {
        return kMinOutput;
    }

    /**
     * @param kMinOutput the kMinOutput to set
     */
    public void setkMinOutput(double kMinOutput) {
        this.kMinOutput = kMinOutput;
    }

    /**
     * @return the kMaxOutput
     */
    public double getkMaxOutput() {
        return kMaxOutput;
    }

    /**
     * @param kMaxOutput the kMaxOutput to set
     */
    public void setkMaxOutput(double kMaxOutput) {
        this.kMaxOutput = kMaxOutput;
    }

    /**
     * @return the kIz
     */
    public double getkIz() {
        return kIz;
    }

    /**
     * @param kIz the kIz to set
     */
    public void setkIz(double kIz) {
        this.kIz = kIz;
    }

    /**
     * @return the kFF
     */
    public double getkFF() {
        return kFF;
    }

    /**
     * @param kFF the kFF to set
     */
    public void setkFF(double kFF) {
        this.kFF = kFF;
    }

    /**
     * @return the kD
     */
    public double getkD() {
        return kD;
    }

    /**
     * @param kD the kD to set
     */
    public void setkD(double kD) {
        this.kD = kD;
    }

    /**
     * @return the kI
     */
    public double getkI() {
        return kI;
    }

    /**
     * @param kI the kI to set
     */
    public void setkI(double kI) {
        this.kI = kI;
    }

    /**
     * @return the kP
     */
    public double getkP() {
        return kP;
    }

    /**
     * @param kP the kP to set
     */
    public void setkP(double kP) {
        this.kP = kP;
    }

}
