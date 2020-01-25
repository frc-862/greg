/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.testing;

import java.util.Iterator;
import java.util.PriorityQueue;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.FaultCode;

public class SystemTestCommand extends CommandBase {
    private static PriorityQueue<SystemTest> tests = new PriorityQueue<>();
    private Iterator<SystemTest> itor;
    private SystemTest current;

    private boolean passedAll = true;

    public SystemTestCommand() {
        for (var test : tests) {
            for (var req : test.getRequirements()) {
                addRequirements(req);
            }
        }
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        itor = tests.iterator();
    }

    private boolean isResting = false;
    
    private double restStarted = 0d;

    private final double restDuration = 3d;

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        if(isResting) {
            if(Timer.getFPGATimestamp() - restStarted < restDuration) return;
            isResting = false;
        }

        if (current == null) {
            current = itor.next();
            current.initialize();
        }

        if (current.isFinished()) {
            current.end(false);
            if (!current.didPass()) {
                FaultCode.write(current.getCode());
                System.out.println("Failed: " + current);
                passedAll = false;
            } else {
                System.out.println("Passed: " + current);
            }
            current = null;

            isResting = true;
            restStarted = Timer.getFPGATimestamp();

        } else {
            current.execute();
        }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        // only done when I am out of of commands,
        // and the last command has finished
        return current == null && !itor.hasNext();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        if (current != null) {
            current.end(interrupted);
        }
        System.out.println("Passed All Tests: " + passedAll);
    }

    public static void register(SystemTest test) {
        tests.add(test);
    }
}
