/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Collector;

public class CollectEject extends CommandBase {

    Collector collector;
    DoubleSupplier collectPwr;
    DoubleSupplier ejectPwr;

    /**
     * Creates a new Collect_Eject.
     */
    public CollectEject(Collector collector, DoubleSupplier collectPwr, DoubleSupplier ejectPwr) {
        this.collector = collector;
        this.collectPwr = collectPwr;
        this.ejectPwr = ejectPwr;
        addRequirements(collector);
    }

    @Override
    public void execute() {

        collector.setPower(collectPwr.getAsDouble() - ejectPwr.getAsDouble());

    }

    @Override
    public void end(boolean interrupted) {

        super.end(interrupted);
        collector.stop();

    }

}
