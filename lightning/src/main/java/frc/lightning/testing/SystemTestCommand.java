/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.testing;

import java.util.Iterator;
import java.util.PriorityQueue;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.fault.FaultCode;

public class SystemTestCommand extends CommandBase {

	private static PriorityQueue<SystemTest> queue = new PriorityQueue<>();

	private Iterator<SystemTest> itor;
	
    private SystemTest current;

	private static final ShuffleboardTab tab = Shuffleboard.getTab("System Tests");

	private static final ShuffleboardLayout functionList = tab.getLayout("User Test Functions", BuiltInLayouts.kList);

	private boolean passedAll;

	private boolean isResting; 
    
	private double restStarted;
	
	private int passed;

	private int failed;

	private final double restDuration = 2d;
	
	private UserIndication userIndication;

	private NetworkTableEntry successButton;

	private NetworkTableEntry failureButton;

	private enum UserIndication {
		NONE, SUCCESS, FAILURE
	}

	private void resetUserIndication() {
		userIndication = UserIndication.NONE;
		if(successButton != null) successButton.setBoolean(false);
		if(failureButton != null) failureButton.setBoolean(false);
	}

	public static void register(SystemTest test) {
		if(queue.isEmpty()) {
			queue.add(new SystemTest("Please Indicate Success to Begin Testing", FaultCode.Codes.GENERAL, SystemTest.Priority.DO_FIRST) {
				@Override
				public boolean didPass() {
					return false;
				}
			});
		}
		queue.add(test);
		tab.getLayout("Tests", BuiltInLayouts.kList).add(test.getName(), queue.size());
	}

	public void reset() {
		passedAll = true;
		isResting = true;
		restStarted = 0d;
		passed = 0;
		failed = 0;
		resetUserIndication();
	}

	public SystemTestCommand() {
		System.out.println("SystemTest.constructor");
		reset();

		tab.addNumber("Number Tests Remaining", () -> queue.size() - passed - failed);

		tab.addNumber("Number Tests Passed", () -> passed);

		tab.addNumber("Number Tests Failed", () -> failed);

		tab.addString("Current Test", () -> {
			if(current != null) return current.getMessage();
			else if(queue.size() - passed - failed == 0) return "All Tests Completed";
			return "";
		});

		tab.addString("User Indication", () -> {
			if(userIndication.equals(UserIndication.SUCCESS)) return "SUCCESS";
			if(userIndication.equals(UserIndication.FAILURE)) return "FAILURE";
			return "NONE";
		});

		successButton = functionList.add("Indicate Success", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
		failureButton = functionList.add("Indicate Failure", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();

		// var successFunct = new InstantCommand(() -> {
		// 	userIndication = UserIndication.SUCCESS;
		// 	//current.cancel();
		// });
		// successFunct.setName("Indicate Success");
		// functionList.add(successFunct);

		// var failureFunct = new InstantCommand(() -> {
		// 	userIndication = UserIndication.FAILURE;
		// 	//current.cancel();
		// });
		//failureFunct.setName("Indicate Failure");
		// functionList.add(failureFunct);

		for (var test : queue) {
            for (var req : test.getRequirements()) {
                addRequirements(req);
            }
		}
		
	}

	@Override
	public void initialize() {
		System.out.println("SystemTest.initialize");
		Shuffleboard.selectTab("System Tests");
		itor = queue.iterator();
	}

	@Override
	public void execute() {

		boolean success = successButton.getBoolean(false);
		boolean failure = failureButton.getBoolean(false);

		if(success && failure) {
			userIndication = UserIndication.NONE;
		} else if(success) {
			userIndication = UserIndication.SUCCESS;
		} else if(failure) {
			userIndication = UserIndication.FAILURE;
		} 
		
		if(isResting) {
            if(Timer.getFPGATimestamp() - restStarted < restDuration) return;
            isResting = false;
        }

        if (current == null) {
			System.out.println("SystemTest.nextTest");
            current = itor.next();
            current.initialize();
		}
		
        if (current.isFinished() || !userIndication.equals(UserIndication.NONE)) {

			current.end(!userIndication.equals(UserIndication.NONE));
			
			if(userIndication.equals(UserIndication.SUCCESS)) {
				System.out.println("SystemTest.passedByUserIndication");
				onPass();
			} else if(userIndication.equals(UserIndication.FAILURE)) {
				System.out.println("SystemTest.failedByUserIndication");
				onFail();
			} else { // no user indication
				if(current.didPass()) onPass();
				else onFail();
			}
			
            current = null;
            isResting = true;
			restStarted = Timer.getFPGATimestamp();

			resetUserIndication();
			
        } else {
            current.execute();
		}
		
	}

	private void onPass() {
		passed++;
		System.out.println("SystemTest.passed:" + current.getName());
	}

	private void onFail() {
		failed++;
		FaultCode.write(current.getCode());
		System.out.println("SystemTest.failed:" + current.getName());
		passedAll = false;
	}

	@Override
	public void end(boolean interrupted) {
		if (current != null) {
            current.end(interrupted);
		}
		System.out.println(passedAll ? "SystemTest.passedAll" : "SystemTest.didNotPassAll");

	}

	@Override
	public boolean isFinished() {
		boolean condition = ((current == null) && (!itor.hasNext()));
		if(condition) System.out.println("");
		return condition;
	}

}
