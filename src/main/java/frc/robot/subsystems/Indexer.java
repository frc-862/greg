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
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {
    private final double pwrThreshold = 0.05;
    private VictorSPX indexer;
    private DoubleSolenoid safety;

    public int ballCount = 0;
    DigitalInput collectSensor;
    DigitalInput ejectSensor;

    /**
     * Creates a new Indexer.
     */
    public Indexer() {
        safety = new DoubleSolenoid(RobotMap.COMPRESSOR_ID, RobotMap.SAFTEY_IN_CHANNEL, RobotMap.SAFTEY_OUT_CHANNEL);
        indexer = new VictorSPX(RobotMap.INDEXER);
        indexer.setInverted(true);
        collectSensor = new DigitalInput(RobotMap.COLLECT_SENSOR);
        ejectSensor = new DigitalInput(RobotMap.EJECT_SENSOR);

        CommandScheduler.getInstance().registerSubsystem(this);

        final var tab = Shuffleboard.getTab("Indexer");
        tab.addBoolean("Collect Sensor", collectSensor::get);
        tab.addBoolean("Shooter Sensor", ejectSensor::get);
    }

    private boolean armed = true;
    private double armedTimer = 0d;
    private boolean ballSeen = false;

    private boolean armedShooter = true;
    private double armedTimerShooter = 0d;
    private boolean ballSeenShooter = false;

    @Override
    public void periodic() {
        ballSeen = !collectSensor.get();
        ballSeenShooter = !ejectSensor.get();

        if (ballSeen) {
            if(armed) {
                 ballCount += 1;
                armed = false;
            }
            armedTimer = Timer.getFPGATimestamp();
        } 
        if (!ballSeen && !armed && ((Timer.getFPGATimestamp() - armedTimer) > 0.1)) { // TODO constant
            armed = true;
        }

        if (ballSeenShooter) {
            if(armedShooter) {
                ballCount += 1;
                armedShooter = false;
            }
            armedTimerShooter = Timer.getFPGATimestamp();
        }
        if (!ballSeenShooter && !armedShooter && ((Timer.getFPGATimestamp() - armedTimerShooter) > 0.1)) { // TODO constant
            armedShooter = true;
        }
    }

    public boolean isBallSeen() { return ballSeen; }
    public void setBallsHeld(int count) { this.ballCount = count; }

    public void stop() {
        setPower(0);
    }

    public void setPower(double pwr) {
        indexer.set(ControlMode.PercentOutput,pwr);
    }

    public void safetyClosed() {
        safety.set(DoubleSolenoid.Value.kForward);
    }

    public void safetyOpen() {
        safety.set(DoubleSolenoid.Value.kReverse);
    }

    public void toggleSafety() {
        if(safety.get().equals(DoubleSolenoid.Value.kForward)) safetyOpen();
        else safetyClosed();
    }

    public void spit() {
        setPower(-1d);
    }

    public void toShooter() {
        setPower(1d);
    }

    public int getPowerCellCount() {
        return ballCount;
    }

    public void resetCount(){
        ballCount = 0;
    }
}
