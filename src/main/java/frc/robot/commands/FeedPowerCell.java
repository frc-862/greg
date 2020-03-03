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
        shooter.resetBallsFired();
        startingPCCount = indexer.getPowerCellCount();
        indexer.safteyOpen();
        startingPCCount = indexer.getBallsHeld();

    }
    @Override
    public void execute() {
        System.out.println("im tryin");
        indexer.setPower(.8);
    }

    @Override
    public void end(boolean interrupted) {
        if (!fullAuto) {
            indexer.safteyClosed();
        }
        indexer.stop();
        shooter.resetBallsFired();
    }

    @Override
    public boolean isFinished() {
        return (indexer.getBallsHeld() == (startingPCCount - 1));
    }
}
