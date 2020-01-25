package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Vision;

public class WaitForVision extends CommandBase {
    private final Vision vision;

    public WaitForVision(Vision vision) {
        this.vision = vision;
    }

    @Override
    public boolean isFinished() {
        return vision.seePortTarget();
    }
}
