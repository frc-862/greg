package frc.lightning.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.logging.DataLogger;

public class DrivetrainLogger extends SubsystemBase {
    private final LightningDrivetrain drivetrain;

    public DrivetrainLogger(LightningDrivetrain dt) {
        this.drivetrain = dt;

        DataLogger.addDataElement("leftVelocity", () -> dt.getLeftVelocity());
        DataLogger.addDataElement("rightVelocity", () -> dt.getRightVelocity());
    }
}
