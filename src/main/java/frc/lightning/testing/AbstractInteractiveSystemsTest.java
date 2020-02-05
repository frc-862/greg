/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.testing;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.util.FaultCode;

/**
 * Add your docs here.
 */
public abstract class AbstractInteractiveSystemsTest extends SystemTest {

    private static Verify verify = new Verify();

    static { 
        SmartDashboard.putData("Verify Systems Test", verify);
    }
    
    private String msg = "";

    public AbstractInteractiveSystemsTest(FaultCode.Codes code, String msg) {
        this(code, Priority.DONT_CARE, msg);
    }

    public AbstractInteractiveSystemsTest(FaultCode.Codes code, Priority priority, String msg) {
        super(code, priority);
        this.msg = msg;
    }

    @Override
    public void initialize() {
        super.initialize();
        SmartDashboard.putString("InteractiveSystemsTest", msg);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        verify.reset();
    }

    @Override
    public boolean isFinished() {
        return verify.isVerified();
    }

    @Override
    public boolean didPass() {
        return false; // verify.isVerified();
    }

}
