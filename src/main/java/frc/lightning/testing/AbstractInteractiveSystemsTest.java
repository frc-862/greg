/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.testing;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.lightning.util.FaultCode;

/**
 * Add your docs here.
 */
public abstract class AbstractInteractiveSystemsTest extends SystemTest {

    BooleanSupplier verify = () -> isVerified();
    
    private boolean isVerified = false;

    private String msg = "";

    public AbstractInteractiveSystemsTest(FaultCode.Codes code, BooleanSupplier verify, String msg) {
        super(code, Priority.DONT_CARE);
        this.verify = verify;
        this.msg = msg;
    }

    public AbstractInteractiveSystemsTest(FaultCode.Codes code, Priority priority, BooleanSupplier verify, String msg) {
        super(code, priority);
        this.verify = verify;
        this.msg = msg;
    }

    @Override
    public void initialize() {
        super.initialize();
        Shuffleboard.getTab("System Test").addString("InteractiveSystemTest", () -> msg);
    }

    @Override
    public boolean didPass() {
        return verify.getAsBoolean();
    }

    public void verify() {
        isVerified = true;
    }

    public boolean isVerified() { return isVerified; }

}
