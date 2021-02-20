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
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {
    
    private VictorSPX indexer;
    private DoubleSolenoid safty;

    public int ballCount = 0;
    DigitalInput collectSensor;
    DigitalInput ejectSensor;

    public int ballsHeld = 0;
    public int ballsFired = 0;

    private double isEmptyTimer =0;

    private SimpleWidget indexerBallCount;

    /**
     * Creates a new Indexer.
     */
    public Indexer() {
        safty = new DoubleSolenoid(RobotMap.COMPRESSOR_ID, RobotMap.SAFTEY_IN_CHANNEL, RobotMap.SAFTEY_OUT_CHANNEL);
        indexer = new VictorSPX(RobotMap.INDEXER);
        indexer.setInverted(true);
        collectSensor = new DigitalInput(RobotMap.COLLECT_SENSOR);
        ejectSensor = new DigitalInput(RobotMap.EJECT_SENSOR);

        CommandScheduler.getInstance().registerSubsystem(this);

        final var tab = Shuffleboard.getTab("Indexer"); // tab was under autonomas
        indexerBallCount = tab.add("StartingBallCount", 3);

    }

    public static Indexer create() {
        return new Indexer();
    }

    private boolean armed = true;
    private double armedTimer = 0d;
    private boolean ballSeen = false;

    private boolean armedShooter = true;
    private double armedTimerShooter = 0d;
    private boolean ballSeenShooter = false;
    private boolean emptyTimer = false;

    @Override
    public void periodic() {
        ballSeen = !collectSensor.get();
        ballSeenShooter = !ejectSensor.get();
        Shuffleboard.getTab("Indexer").add("BallSeenBeamBreak", ballSeen);
                // SmartDashboard.putNumber("BallsHeld", ballCount);
        Shuffleboard.getTab("Indexer").add("BallsHeld", ballsHeld);
        Shuffleboard.getTab("Indexer").add("IndexerArmed", armed);
        Shuffleboard.getTab("Indexer").add("is Empty", isEmpty());

        
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

        if(ballSeenShooter) {
            if(armedShooter) {
                ballsFired++;
                armedShooter = false;
            }
            armedTimerShooter = Timer.getFPGATimestamp();
        }
        if(!ballSeenShooter && !armedShooter && ((Timer.getFPGATimestamp() - armedTimerShooter) > 0.1)) { // TODO constant
            armedShooter = true;
        }

        ballsHeld = ballCount - ballsFired;

        if (ballsHeld == 0 && !emptyTimer) {
            isEmptyTimer = Timer.getFPGATimestamp();
            emptyTimer=true;
        } else {
            emptyTimer =false;
            isEmptyTimer = 0;
        }
    }

    public boolean isEmpty() {
        if (Timer.getFPGATimestamp() - isEmptyTimer >= .125 && emptyTimer) {
            return true;
        }else {
            return false;
        }
    }


    public boolean isBallSeen() { return ballSeen; }

    public void resetBallCount() { /*ballCount = 0;*/ }

    public void setBallsHeld() { 
        int balls = (int) indexerBallCount.getEntry().getNumber(3);
        this.ballCount = balls;
        this.ballsFired = 0;
        this.ballsHeld = balls;
    }

    public int getBallCount() { return ballsHeld; }
    
    public int getBallsHeld() { return ballsHeld; }

    public void shotBall() { /*ballCount--;*/ }

    public void stop() {
        setPower(0);
    }

    public void setPower(double pwr) {
        indexer.set(ControlMode.PercentOutput,pwr);
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
        return ballsHeld;
    }

    public void reastBallsHeld(){
        ballCount = 0;
        ballsFired = 0;
    }
    
}
