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
import frc.robot.subsystems.Vision;

public class SpinUpFlywheelVelocity extends CommandBase {

    Shooter shooter;
    Vision vision;

    /**
     * Creates a new Fire.
     */
    public SpinUpFlywheelVelocity(Shooter shooter, Vision vision) {
        this.shooter = shooter;
        this.vision = vision;

        addRequirements(shooter);
    }

    // Called when command starts
    @Override
    public void initialize() {
        shooter.setShooterVelocity(vision.getBestShooterVelocity());
        shooter.setBackspin(vision.getBestShooterBackspin());
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return shooter.atSetPoint();
    }
}
