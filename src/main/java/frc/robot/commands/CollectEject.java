/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

import java.util.function.DoubleSupplier;

public class CollectEject extends CommandBase {

    Collector collector;
    Indexer indexer;
    DoubleSupplier collectPwr;
    DoubleSupplier ejectPwr;

    /**
     * Creates a new Collect_Eject.
     */
    public CollectEject(Collector collector,Indexer indexer, DoubleSupplier collectPwr) {
        this.collector = collector;
        this.collectPwr = collectPwr;

        addRequirements(collector,indexer);
    }

    @Override
    public void execute() {

        collector.setPower(collectPwr.getAsDouble());
        indexer.setPower(collectPwr.getAsDouble());

    }

    @Override
    public void end(boolean interrupted) {

        super.end(interrupted);
        collector.stop();

    }

}
