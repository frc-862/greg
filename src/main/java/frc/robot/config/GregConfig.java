package frc.robot.config;

import frc.lightning.LightningConfig;
import frc.lightning.util.RamseteGains;

public class GregConfig extends LightningConfig {

    @Override
    public double getTicsPerRev() {
        return 2048;
    }

    @Override
    public double getTicsPerRevWheel() {
        return (getTicsPerRev() * getGearRatio());
    }

    @Override
    public double getMaxRPM() {
        return 7500;
    }

    @Override
    public double getWheelDiameterInches() {
        return 6;
    }

    @Override
    public double getGearRatio() {
        return 15; // 15:1
    }

    @Override
    public double getOpenLoopRamp() {
        return 0.6; // 0.5
    }

    @Override
    public double getCloseLoopRamp() {
        return 0.6; // 0.5
    }

    @Override
    public RamseteGains getRamseteGains() {
        return new RamseteGains(0.68284937, // trackWidth
                0.172, // kS
                2.54, // kV - 254 . . . these numbers must work!
                0.44, // kA
                0.0353, // left_kP
                0d, // left_kI
                0d, // left_kD
                0.0353, // right_kP
                0d, // right_kI
                0d, // right_kD
                5.4d, // maxVelocity (ft/sec)
                5d); // maxAcceleration (ft/sec^2)
    }

}
