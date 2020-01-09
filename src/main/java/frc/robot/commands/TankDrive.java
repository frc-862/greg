package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.JoystickFilter;

import java.util.function.DoubleSupplier;

public class TankDrive extends CommandBase {
    private final LightningDrivetrain drivetrain;
    private final DoubleSupplier leftThrottle;
    private final DoubleSupplier rightThrottle;
    private double deadband = 0.1;
    private double minPower = 0.1;
    private double maxPower = 1.0;
    private final JoystickFilter filter = new JoystickFilter(deadband, minPower, maxPower, JoystickFilter.Mode.CUBED);

    public TankDrive(LightningDrivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
        this.drivetrain = drivetrain;
        this.leftThrottle = left;
        this.rightThrottle = right;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        final double targetLeft = filter.filter(leftThrottle.getAsDouble());
        final double targetRight = filter.filter(rightThrottle.getAsDouble());

        drivetrain.setPower(targetLeft, targetRight);
    }
}
