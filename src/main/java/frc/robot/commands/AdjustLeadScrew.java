package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.LeadScrew;

public class AdjustLeadScrew extends CommandBase {
    private final LeadScrew leadScrew;
    private final double desiredAngle;

    public AdjustLeadScrew(LeadScrew ls, double angle) {
        this.leadScrew = ls;
        desiredAngle = angle;
    }

    @Override
    public void initialize() {
        leadScrew.setAngle(desiredAngle);
    }

    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(desiredAngle, leadScrew.getAngle(), Constants.leadScrewTolerance);
    }
}
