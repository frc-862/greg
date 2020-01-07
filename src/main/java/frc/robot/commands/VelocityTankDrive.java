/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.JoystickFilter;
import frc.robot.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

public class VelocityTankDrive extends CommandBase {
    private final Drivetrain drivetrain;
    private final DoubleSupplier leftThrottle;
    private final DoubleSupplier rightThrottle;
    private double deadband = 0.1;
    private double minPower = 0.1;
    private double maxPower = 1.0;
    private final JoystickFilter filter = new JoystickFilter(deadband, minPower, maxPower, JoystickFilter.Mode.CUBED);

    public VelocityTankDrive(Drivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
        this.drivetrain = drivetrain;
        this.leftThrottle = left;
        this.rightThrottle = right;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        final double targetLeft = filter.filter(leftThrottle.getAsDouble());
        final double targetRight = filter.filter(rightThrottle.getAsDouble());

        SmartDashboard.putNumber("Left Target", targetLeft);
        SmartDashboard.putNumber("Right Target", targetRight);

        drivetrain.setVelocity(targetLeft, targetRight);
    }
}