package frc.lightning.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PigeonSubsystem extends SubsystemBase implements LightningIMU {
    private final PigeonIMU bird;

    public PigeonSubsystem(int canid) {
        bird = new PigeonIMU(canid);
        bird.configFactoryDefault();
        resetHeading();
    }

    @Override
    public void resetHeading() {
        bird.setYaw(0d);
    }

    @Override
    public double getHeading() {
        double[] ypr = new double[3];

        bird.getYawPitchRoll(ypr);
        return ((ypr[0] + 180) % 360) - 180;
    }
}
