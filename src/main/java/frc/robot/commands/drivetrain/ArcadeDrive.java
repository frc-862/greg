/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.commands.CurvatureDrive;
import frc.lightning.util.JoystickFilter;

import java.util.function.DoubleSupplier;

/**
 * An example command that uses an example subsystem.
 */
public class ArcadeDrive extends CommandBase {
    private final LightningDrivetrain drivetrain;
    private final DoubleSupplier throttle;
    private final DoubleSupplier turn;
    private final CurvatureDrive curvatureDrive;
    private double deadband = 0.1;
    private double minPower = 0.1;
    private double maxPower = 1.0;
    private final JoystickFilter throttleFilter = new JoystickFilter(deadband, minPower, maxPower, JoystickFilter.Mode.CUBED);
    private final JoystickFilter turnFilter = new JoystickFilter(deadband, 0, maxPower, JoystickFilter.Mode.LINEAR);

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ArcadeDrive(LightningDrivetrain subsystem, DoubleSupplier throttle, DoubleSupplier turn) {
        drivetrain = subsystem;
        this.throttle = throttle;
        this.turn = turn;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain);

        curvatureDrive = new CurvatureDrive();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        final double speed = throttleFilter.filter(throttle.getAsDouble());
        final double rotation = turnFilter.filter(turn.getAsDouble());
        final boolean quickTurn = Math.abs(speed) < 0.01 && Math.abs(rotation) > 0.1;

        final var cmd = curvatureDrive.curvatureDrive(speed, rotation, quickTurn);
        drivetrain.setPower(cmd);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
