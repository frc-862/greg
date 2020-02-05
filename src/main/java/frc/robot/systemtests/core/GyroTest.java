/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.systemtests.core;

import java.util.function.BooleanSupplier;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.testing.AbstractInteractiveSystemsTest;
import frc.lightning.util.FaultCode;
import frc.lightning.util.FaultCode.Codes;

/**
 * Add your docs here.
 */
public class GyroTest extends AbstractInteractiveSystemsTest {

    private final double threshold = 5; 
    private LightningDrivetrain drivetrain;
    private double initYaw;

    public GyroTest(LightningDrivetrain drivetrain) {
        super(FaultCode.Codes.NAVX_ERROR, "Turn The Robot a Bit Homie");
        this.drivetrain = drivetrain;
    }

    @Override
    public void initialize() {
        super.initialize();
        initYaw = drivetrain.getHeading().getDegrees();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

    @Override
    public boolean didPass() {
        return ((Math.abs(drivetrain.getHeading().getDegrees()) - threshold) > initYaw);
    }

}
