/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;

public class VisionRotate extends CommandBase {
    private static final double VISION_ROTATE_P = 1.0 / 200;
    LightningDrivetrain drivetrain;
    Vision vision;

    /**
     * Creates a new Aim.
     */
    public VisionRotate(LightningDrivetrain drivetrain, Vision vision) {
        this.drivetrain = drivetrain;
        this.vision = vision;

        // note not adding vision on purpose (multi-use subsystem)
        addRequirements(drivetrain);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (vision.seePortTarget()) {
            double visionOffset = vision.getOffsetAngle();
            double pwr = visionOffset * VISION_ROTATE_P;
            drivetrain.setVelocity(pwr, -pwr);
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return vision.seePortTarget() && LightningMath.epsilonEqual(vision.getOffsetAngle(), 0, Constants.ROTATION_TOLERANCE);
    }
}
