package frc.robot.config;

import frc.lightning.LightningConfig;
import frc.lightning.util.RamseteGains;

public class QuasarConfig extends LightningConfig {

    @Override
    public double getTicsPerRev() {
        return 2048d;
    }

    @Override
    public double getMaxRPM() {
        return 5700d;
    }

    @Override
    public double getWheelDiameterInches() {
        return 6.16d;
    }

    @Override
    public double getGearRatio() {
        return 15; // 15:1
    }

    @Override
    public double getTicsPerRevWheel() {
        return (getTicsPerRev() * getGearRatio());
    }

    @Override
    public RamseteGains getRamseteGains() {
        return new RamseteGains(0.5583711759, // trackWidth // TODO tune these
                0.136, // kS
                2.51, // kV - 254 . . . these numbers must work!
                0.318, // kA
                0.121, // left_kP
                0d, // left_kI
                0d, // left_kD
                0.121, // right_kP
                0d, // right_kI
                0d, // right_kD
                5.4d, // maxVelocity (ft/sec)
                5d); // maxAcceleration (ft/sec^2)
    }

    @Override
    public double getOpenLoopRamp() {
        return 0.15;
    }

    @Override
    public double getCloseLoopRamp() {
        return 0;
    }
}
