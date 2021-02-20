package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.LeadScrew;
import frc.robot.subsystems.Vision;

public class VisionLeadScrew extends CommandBase {
    private final LeadScrew leadScrew;
    private final Vision vision;

    public VisionLeadScrew( LeadScrew ls, Vision vision) {

        this.leadScrew = ls;

        // Intentionally not adding vision as a requirement
        // our use is "read only"
        this.vision = vision;

        addRequirements(leadScrew);
    }

    @Override
    public void initialize() {
        leadScrew.enableAutoAdjust();
        leadScrew.setAngle(vision.getBestLeadScrew());
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(leadScrew.getAngle(),
                                          vision.getBestLeadScrew(),
                                          Constants.leadScrewTolerance);
    }

    @Override
    public void end(boolean interrupted) {

    }
}
