package frc.lightning.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.LightningConfig;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.JoystickFilter;

public class VoltDrive extends CommandBase {

    LightningDrivetrain drivetrain;
    DoubleSupplier left;
    DoubleSupplier right;
    private double deadband = 0.15;
    private double minPower = 0.1;
    private double maxPower = 1.0;
    private final JoystickFilter filter = new JoystickFilter(deadband, minPower, maxPower, JoystickFilter.Mode.CUBED);

    public VoltDrive(LightningDrivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
        this.drivetrain = drivetrain;
        this.left = left;
        this.right = right;
        addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        SmartDashboard.putNumber("RightVolts", drivetrain.getRightVolts());
        SmartDashboard.putNumber("LeftVolts", drivetrain.getLeftVolts());
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftVolts = filter.filter(left.getAsDouble());
        double rightVolts = filter.filter(right.getAsDouble());

        leftVolts  *= LightningConfig.VOLT_LIMIT;
        rightVolts *= LightningConfig.VOLT_LIMIT;

        SmartDashboard.putNumber("RightVolts", drivetrain.getRightVolts());
        SmartDashboard.putNumber("LeftVolts", drivetrain.getLeftVolts());

        drivetrain.setOutput(leftVolts, rightVolts);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        drivetrain.stop();
    }

}