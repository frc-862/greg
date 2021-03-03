/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.auto.Path;
import frc.lightning.auto.Paths;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Indexer;

/**
 * Drives given path while collecting and indexing.
 */
public class GalacticSearchCommand extends CommandBase {

	private final String nullState = "NONE";

	public static final String PROCESS_REQUEST_ENTRY_NAME = "DeterminePath";

    public static final String INFERENCE_RESULT_ENTRY_NAME = "DeterminedPath";

	private LightningDrivetrain drivetrain;

	private Collector collector;

	private Indexer indexer;

    private NetworkTable ntab;

    private NetworkTableEntry processReq;

    private NetworkTableEntry processRes;

	public GalacticSearchCommand(LightningDrivetrain drivetrain, Collector collector, Indexer indexer) {

		// Vision Network Table
        ntab = NetworkTableInstance.getDefault().getTable("Vision");

        // Process Request Entry
        processReq = ntab.getEntry(PROCESS_REQUEST_ENTRY_NAME);

        // Process Results Entry
        processRes = ntab.getEntry(INFERENCE_RESULT_ENTRY_NAME);

		// final var vision_tab = Shuffleboard.getTab("Vision");
		// vision_tab.addBoolean("DeterminePath", this::isPathNull);

		this.drivetrain = drivetrain;
		this.collector = collector;
		this.indexer = indexer;

		// pathName = vision_tab.add("DeterminedPath", nullState).withWidget(BuiltInWidgets.kTextView).getEntry();
		// pathName = NetworkTableInstance.getDefault().getTable("Vision").getEntry("DeterminedPath");

	}

	private boolean isPathNull() {
		if(processRes != null) return processRes.getString(nullState).equals(nullState);
		return false;
	}

	@Override
	public void initialize() {
		super.initialize();
		processRes.setString(nullState);
		processReq.setBoolean(true);
	}

	@Override
	public boolean isFinished() {
		return !isPathNull();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		processReq.setBoolean(false);
		Path path = Paths.getPath(processRes.getString(nullState));
		(new CollectPathCommand(drivetrain, collector, indexer, path)).schedule();
	}

}
