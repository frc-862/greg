/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

@Deprecated
public class AutoIndexThree extends CommandBase {

    final Indexer indexer;

    /**
     * Creates a new Collect_Eject.
     */
    public AutoIndexThree(Indexer indexer) {
        this.indexer = indexer;

        addRequirements(indexer);
    }

    double indexTimer = 0d;

    @Override
    public void execute() {

        if(indexer.isBallSeen()) {

            if(indexer.ballCount <= 4) {
                indexTimer = Timer.getFPGATimestamp();
            } 

            if((Timer.getFPGATimestamp() - indexTimer) < 2d) {
                indexer.setPower(1d);
            } else {
                indexer.setPower(0d);
            }
            
        } else {
            indexer.setPower(0d);
        }

    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        indexer.stop();
    }

}
