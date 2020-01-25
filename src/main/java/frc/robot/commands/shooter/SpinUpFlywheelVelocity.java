/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;

public class SpinUpFlywheelVelocity extends CommandBase {

    Shooter shooter;
    double velocity;

    /**
     * Creates a new Fire.
     */
    public SpinUpFlywheelVelocity(Shooter shooter, double velocity) {
        this.shooter = shooter;
        this.velocity = velocity;

        addRequirements(shooter);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.setShooterVelocity(velocity);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return LightningMath.epsilonEqual(shooter.getFlywheelMotor1Velocity(), velocity, Constants.FLYWHEEL_EPSILON) &&
               LightningMath.epsilonEqual(shooter.getFlywheelMotor2Velocity(), velocity, Constants.FLYWHEEL_EPSILON) &&
               LightningMath.epsilonEqual(shooter.getFlywheelMotor3Velocity(), velocity, Constants.FLYWHEEL_EPSILON);
    }
}
