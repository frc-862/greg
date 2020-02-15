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
        shooter.reeeeesetBallsFired();
        startingPCCount = indexer.getPowerCellCount();
        indexer.safetyOff();
        indexer.feed();
    }

    @Override
    public void end(boolean interrupted) {
        if (!fullAuto) {
            indexer.safetyOn();
        }
        indexer.stop();
        shooter.reeeeesetBallsFired();
    }

    @Override
    public boolean isFinished() {
        // need to consider the case of being fed PC
        // while shooting, and the count going up?
//        return startingPCCount > indexer.getPowerCellCount();
        if (shooter.getBallsFired() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
