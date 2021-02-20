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

public class Collect extends CommandBase {

	final Collector collector;
	final DoubleSupplier collectPwr;

	/**
	 * Creates a new Collect.
	 */
	public Collect(Collector collector, DoubleSupplier collectPwr) {
		this.collector = collector;
		this.collectPwr = collectPwr;
		addRequirements(collector);
	}

	@Override
	public void execute() {

		if (collectPwr.getAsDouble() > .2) {
			collector.collect();
		} else {
			collector.setPower(collectPwr.getAsDouble());
		}

	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		collector.stop();
	}

}
