/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotConstants;
import frc.robot.RobotMap;
import frc.robot.robots.GregContainer;

public class Indexer extends SubsystemBase {
    DigitalInput[] pcSensors;
    private final double pwrThreshold = 0.05;
    private VictorSPX indexer;
    private DoubleSolenoid safty;

    public int ballCount;
    DigitalInput collectSensor;

    // Components
    /**
     * Creates a new Indexer.
     */

    public Indexer(DigitalInput[] sensors) {
        safty = new DoubleSolenoid(RobotMap.COMPRESSOR_ID, RobotMap.SAFTEY_IN_CHANNEL, RobotMap.SAFTEY_OUT_CHANNEL);
        pcSensors = sensors;
        indexer = new VictorSPX(RobotMap.INDEXER);
        indexer.setInverted(true);
        collectSensor = new DigitalInput(RobotMap.COLLECT_SENSOR);

        CommandScheduler.getInstance().registerSubsystem(this);
    }

    public static Indexer create() {
        DigitalInput[] pcSensors = new DigitalInput[5];
        for (var i = 0; i < pcSensors.length; ++i) {
            pcSensors[i] = new DigitalInput(RobotConstants.firstPCSensor + i);
        }
        return new Indexer(pcSensors);
    }

    private boolean armed = true;
    private double armedTimer = 0d;
    private boolean ballSeen = false;

    @Override
    public void periodic() {

        ballSeen = !collectSensor.get();
        SmartDashboard.putBoolean("BallSeenBeamBreak", ballSeen);
        SmartDashboard.putNumber("BallsHeld", ballCount);
        SmartDashboard.putBoolean("IndexerArmed", armed);

        if(ballSeen) {
            if(armed) {
                ballCount++;
                armed = false;
            }
            armedTimer = Timer.getFPGATimestamp();
        } 
        if(!ballSeen && !armed && ((Timer.getFPGATimestamp() - armedTimer) > 0.1)) { // TODO constant
            armed = true;
        }
        
    }

    public boolean isBallSeen() { return ballSeen; }

    public void resetBallCount() { ballCount = 0; }

    public int getBallCount() { return ballCount; }

    public void stop() {
        setPower(0);
    }

    public void feed() {

    }

    public void setPower(double pwr) {
        indexer.set(ControlMode.PercentOutput,pwr);
    }

    /**
     * if ball passes through beam break and indexer moving forward, then add ball
     * if ball passes through beam break and indexer moving backward, then bye bye ball
     * else do nothing - ideling
     */
    private void updateBallCount() {
        // if(getBeamBreak() && (getPower() > pwrThreshold)) GregContainer.setPowerCellCapacity(GregContainer.getPowerCellCapacity() + 1);
        // else if (getBeamBreak() && (getPower() < -pwrThreshold)) GregContainer.setPowerCellCapacity(GregContainer.getPowerCellCapacity() - 1);
    }

    private boolean getBeamBreak() {
        return false;
    }

    public double getPower() {
        return 0d;
    }

    public void safteyClosed() {
        safty.set(DoubleSolenoid.Value.kForward);
    }

    public void safteyOpen() {
        safty.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleSaftey() {
        if(safty.get().equals(DoubleSolenoid.Value.kForward)) safteyOpen();
        else safteyClosed();   
    }

    public void spit() {
        setPower(-1d);
    }

    public void toShooter() {
        setPower(1d);
    }

    public int getPowerCellCount() {
        return 0;
    }
}
