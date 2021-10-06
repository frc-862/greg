/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
/**
 * Drives given path while collecting and indexing.
 */
public class GalacticSearchIdentifier extends CommandBase {

	private final String nullState = "NONE";

	public static final String PROCESS_REQUEST_ENTRY_NAME = "DeterminePath";

    public static final String INFERENCE_RESULT_ENTRY_NAME = "DeterminedPath";

    private NetworkTable ntab;

    private NetworkTableEntry processReq;

    private NetworkTableEntry processRes;

	public GalacticSearchIdentifier() {

		// Vision Network Table
        ntab = NetworkTableInstance.getDefault().getTable("Vision");

        // Process Request Entry
        processReq = ntab.getEntry(PROCESS_REQUEST_ENTRY_NAME);

        // Process Results Entry
        processRes = ntab.getEntry(INFERENCE_RESULT_ENTRY_NAME);

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
	}

}
