package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Indexer;

public class FeedPowerCell extends CommandBase {
    private final Indexer indexer;
    private final boolean fullAuto;
    private int startingPCCount;

    public FeedPowerCell(Indexer index) {
        this.indexer = index;
        this.fullAuto = false;
        addRequirements(indexer);
    }

    public FeedPowerCell(Indexer index, boolean fullAuto) {
        this.indexer = index;
        this.fullAuto = fullAuto;
        addRequirements(indexer);
    }

    @Override
    public void initialize() {
        startingPCCount = indexer.getPowerCellCount();
        indexer.safetyOff();
        indexer.feed();
    }

    @Override
    public void end(boolean interrupted) {
        if (!fullAuto) indexer.safetyOn();
        indexer.stop();
    }

    @Override
    public boolean isFinished() {
        // need to consider the case of being fed PC
        // while shooting, and the count going up?
        return startingPCCount > indexer.getPowerCellCount();
    }
}
