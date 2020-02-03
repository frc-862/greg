package frc.lightning.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NavXIMU extends SubsystemBase implements LightningIMU {
    private final AHRS navx;

    public NavXIMU() {
        navx = new AHRS(I2C.Port.kMXP);
    }

    @Override
    public double getHeading() {
        return -navx.getAngle();
    }
}
