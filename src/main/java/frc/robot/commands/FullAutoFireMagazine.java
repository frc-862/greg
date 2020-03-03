package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterAngle;
import frc.robot.subsystems.Vision;

import java.util.function.DoubleSupplier;

public class FullAutoFireMagazine extends PerpetualCommand {
    private final Indexer indexer;
    private final Shooter shooter;
    private int fired = 0;

    public FullAutoFireMagazine(DoubleSupplier spwr, LightningDrivetrain dt, Vision v, Shooter s, ShooterAngle sa, Indexer i) {
        super(new FullAutoFireOne(dt, v, s, sa, i,true));
        // note not requiring indexer because we know FullAutoFireOne does
        this.indexer = i;
        this.shooter = s;
        fired++;
    }

    double entryTime;

    @Override
    public void initialize(){
        entryTime = Timer.getFPGATimestamp();
        fired = 0;
    }
    @Override
    public boolean isFinished() {
//        if (shooter.getBallsFired() == 5) {
//            return true;
//        } else {
//            return false;
//        }
        // return fired == 5;
        return ((entryTime - Timer.getFPGATimestamp()) > 5d);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        indexer.safetyClosed();
    }
}

