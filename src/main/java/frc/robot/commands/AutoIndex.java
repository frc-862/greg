package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.lightning.util.StatefulCommand;
import frc.robot.subsystems.Indexer;

public class AutoIndex extends StatefulCommand {
    final private double indexTime = 0.2;
    final private double finalIndexTime = 0.08;
    final private int maxBallCount = 5;

    private enum States { idle, collecting, collectinglast, indexing, full };

    private final Indexer indexer;

    public AutoIndex(Indexer indexer) {
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

    @Override
    public void execute() {
        super.execute();
        Shuffleboard.getTab("Indexer").add("Index State", getState().toString());
    }

    public void idleEnter() {
        indexer.setPower(0d);
    }

    public void idle() {
        if (indexer.isBallSeen()) {
            if (indexer.ballsHeld<maxBallCount){
                setState(States.collecting);
            }else if(indexer.ballsHeld==maxBallCount){
                setState(States.collectinglast);
            }
//            setState((indexer.getBallCount() < (maxBallCount)) ? States.collecting : States.collectinglast);
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

    public void collectinglastEnter() {
        indexer.setPower(1d);
    }

    public void collectinglast() {
//        System.out.println("On Last Ball YAY esk sksksksk");
//        indexer.setPower(1d);
        if (this.timeInState() > finalIndexTime) setState(States.full);
    }

    public void fullEnter() {
        indexer.setPower(0);
    }

    public void full() {
        if (indexer.getPowerCellCount() < 5) setState(States.idle);
    }
}

