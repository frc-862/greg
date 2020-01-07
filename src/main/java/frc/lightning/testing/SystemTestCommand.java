/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.testing;

import java.util.Iterator;
import java.util.PriorityQueue;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.FaultCode;

public class SystemTestCommand extends CommandBase {
    private static PriorityQueue<SystemTest> tests = new PriorityQueue<>();
    private Iterator<SystemTest> itor;
    private SystemTest current;

    public SystemTestCommand() {
        for (var test : tests) {
            for (var ss : test.requiresMultiple()) {
                if (ss != null) {
                    this.addRequirements(ss);
                }
            }
        }
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        itor = tests.iterator();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        if (current == null) {
            current = itor.next();
            current.starting();
            current.setup();
        }

        if (current.isFinished()) {
            current.tearDown();
            if (!current.didPass()) {
                FaultCode.write(current.getCode());
            }
            current = null;

        } else {
            current.periodic();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return itor.hasNext();
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        if (current != null) {
            current.tearDown();
        }
    }

    public static void register(SystemTest test) {
        tests.add(test);
    }
}
