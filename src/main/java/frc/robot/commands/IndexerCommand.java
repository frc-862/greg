package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.StatefulCommand;
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends StatefulCommand {
    final private double indexTime = 0.7;
    final private double finalIndexTime = 0.1;
    final private int maxBallCount = 5;

    private enum States { idle, collecting, collectingLast, indexing, full };

    private final Indexer indexer;

    public IndexerCommand(Indexer indexer) {
        super(States.idle);

        this.indexer = indexer;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.indexer);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        this.setState(States.idle);
        super.initialize();
    }

    public void idleEnter() {
        indexer.setPower(0d);
    }

    public void idle() {
        if (indexer.isBallSeen()) {
            setState(indexer.getBallCount() < (maxBallCount - 1) ? States.collecting : States.collectingLast);
        }
    }

    public void collectingEnter() {
        indexer.setPower(1d);
    }

    public void collecting() {
        if (!indexer.isBallSeen()) {
            setState(States.indexing);
        }
    }

    public void indexing() {
        if (this.timeInState() > indexTime) setState(States.idle);
    }

    public void collectingLastEnter() {
        indexer.setPower(1d);
    }

    public void collectingLast() {
        if (timeInState() >= finalIndexTime) setState(States.full);
    }

    public void fullEnter() {
        indexer.setPower(0);
    }

    public void full() {
        if (indexer.getPowerCellCount() < 5) setState(States.idle);
    }
}

