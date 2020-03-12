package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class FeedPowerCell extends CommandBase {
    private final Indexer indexer;
    private final boolean fullAuto;
    private final Shooter shooter;
    private int startingPCCount;

    public FeedPowerCell(Indexer index, Shooter shooter, boolean fullAuto) {
        this.indexer = index;
        this.fullAuto = fullAuto;
        this.shooter = shooter;
        addRequirements(indexer);
    }

    @Override
    public void initialize() {
        startingPCCount = indexer.getPowerCellCount();
        indexer.safetyOpen();
    }

    @Override
    public void execute() {
        indexer.setPower(1);
    }

    @Override
    public void end(boolean interrupted) {
        if (!fullAuto) {
            indexer.safetyClosed();
        }
        indexer.stop();
        indexer.resetCount();
    }

    @Override
    public boolean isFinished() {
        return (indexer.getPowerCellCount() == (startingPCCount - 1));
    }
}
