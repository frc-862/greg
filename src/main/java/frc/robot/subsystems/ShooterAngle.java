package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.LightningMath;
import frc.lightning.util.MovingAverageFilter;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;

import java.io.*;
import java.util.function.DoubleSupplier;

public class ShooterAngle extends SubsystemBase {
    // Greg is at 9º
    public static double low_angle = (Robot.isIllusion() ? 11 : 9);
    public static double high_angle = 38;    // ?? for illusion
    public static int REVERSE_SENSOR_LIMIT = 256;
    public static int FORWARD_SENSOR_LIMIT = 311;
    private final int SENSOR_SAFETY = 4;
    private boolean autoAdjust = false;

    private double setPoint = 20.0;
    private double Kp = .4;
    private double offset = 0;

    private TalonSRX adjuster;

    public ShooterAngle () {
        adjuster = new TalonSRX(RobotMap.SHOOTER_ANGLE);
        readLimits();

        adjuster.configForwardSoftLimitEnable(false);
        adjuster.configForwardSoftLimitThreshold(FORWARD_SENSOR_LIMIT + SENSOR_SAFETY);
        adjuster.configReverseSoftLimitEnable(false);
        adjuster.configReverseSoftLimitThreshold(REVERSE_SENSOR_LIMIT - SENSOR_SAFETY);

//        adjuster.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 10);
//        adjuster.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed, 10);

        adjuster.configReverseSoftLimitThreshold(REVERSE_SENSOR_LIMIT);
        adjuster.configForwardSoftLimitThreshold(FORWARD_SENSOR_LIMIT);

        //motion magic configs
        adjuster.config_kF(0, Constants.kAdjusterF);
        adjuster.config_kD(0, Constants.kAdjusterD);
        adjuster.config_kI(0, Constants.kAdjusterI);
        adjuster.config_kP(0, Constants.kAdjusterP);

        adjuster.configMotionCruiseVelocity(8, 0);
        adjuster.configMotionAcceleration(8, 0);

        //encoder config
        adjuster.configSelectedFeedbackSensor(FeedbackDevice.Analog,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

        CommandScheduler.getInstance().registerSubsystem(this);

        Shuffleboard.getTab("Shooter").addNumber("Shooter Angle", this::getAngle);
        Shuffleboard.getTab("Shooter").addBoolean("Shooter Rev Limit", this::atLowerLimit);
        Shuffleboard.getTab("Shooter").addBoolean("Shooter Fwd Limit", this::atUpperLimit);
    }

    @Override
    public void periodic() {
        if (autoAdjust) {
            adjusterControlLoop();
        }

        final var rawPosition = adjuster.getSelectedSensorPosition();
        if (atUpperLimit()) {
            FORWARD_SENSOR_LIMIT = rawPosition;
            high_angle = getAngle();
        }

        if (atLowerLimit()) {
            REVERSE_SENSOR_LIMIT = rawPosition;
        }
    }

    public void enableAutoAdjust() { autoAdjust = true; }
    public void disableAutoAdjust() { autoAdjust = false; }

    final String filename = "/home/lvuser/angle_limits.dat";
    public void writeLimits() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeInt(FORWARD_SENSOR_LIMIT);
            dos.writeInt(REVERSE_SENSOR_LIMIT);
            dos.writeDouble(high_angle);
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public void readLimits() {
        if (new File(filename).canRead()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
                FORWARD_SENSOR_LIMIT = dis.readInt();
                REVERSE_SENSOR_LIMIT = dis.readInt();
                high_angle = dis.readDouble();
            } catch (IOException e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }

    public void adjusterControlLoop() {
        offset = setPoint - getAngle();
        SmartDashboard.putNumber("Angle setPoint", setPoint);
        SmartDashboard.putNumber("Angle getAngle", getAngle());
        SmartDashboard.putNumber("Angle offset", offset);

        if (!(LightningMath.epsilonEqual(setPoint, offset,2))) {
            SmartDashboard.putNumber("Angle setPower", LightningMath.constrain((offset)*Kp,-1,1));
            setPower(LightningMath.constrain((offset)*Kp,-1,1));
        } else {
            setPower(0);
        }
    }

    public void setAngle(double angle) {
        System.out.println("Set Angle " + angle);
        setPoint = LightningMath.constrain(angle, low_angle, 38);
    }

    public void setPower(double pwr){
        adjuster.set(ControlMode.PercentOutput, pwr);
    }

    private final MovingAverageFilter filter = new MovingAverageFilter(1);
    public double getAngle() {
        double pos = filter.filter(adjuster.getSelectedSensorPosition());
        return (pos - REVERSE_SENSOR_LIMIT) / 2d
                + low_angle;
//        return adjuster.getSelectedSensorPosition(Constants.kPIDLoopIdx);
    }

    public DoubleSupplier getMin(){
        return ()->REVERSE_SENSOR_LIMIT;
    }
    public DoubleSupplier getMax(){
        return ()->FORWARD_SENSOR_LIMIT;
    }

    public boolean atUpperLimit() {
        return adjuster.isFwdLimitSwitchClosed() == 1;
    }

    public boolean atLowerLimit() {
        return adjuster.isRevLimitSwitchClosed() == 1;
    }
}
