/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.logging.CommandLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;

public class VisionRotate extends CommandBase {
    private static final double VISION_ROTATE_P = .1 / 320;
    private static final double MIN_ROTATE_PWR = 0.05;

    private CommandLogger logger = new CommandLogger("VisionRotate");

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

        logger.addDataElement("error");
        logger.addDataElement("power");
//        Shuffleboard.getTab("Vision").addBoolean("Rotate Tolerance", this::inTolerance);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        vision.ringOn();

        double visionOffset = vision.getOffsetAngle();
        double pwr = visionOffset * VISION_ROTATE_P;

        logger.set("error", visionOffset);
        logger.set("power", pwr);

        //System.out.println("VR exec: " + pwr);
        //pwr = Math.max(Math.abs(pwr), MIN_ROTATE_PWR);

        if (visionOffset > 0) {
            pwr = MIN_ROTATE_PWR;
        }
        if (visionOffset < 0) {
            pwr = -MIN_ROTATE_PWR;
        }

        if (Math.abs(vision.getOffsetAngle()) < 10) {
            pwr = 0;
        }

        drivetrain.setOutput(12 * pwr, -pwr * 12);
    }

    private boolean inTolerance() {
        // note 8 is in pixels, not degrees
        // Field of view ~70º / 640 pixels = ~0.1º per pixel, 8 pixels is less than one degree
        return LightningMath.epsilonEqual(vision.getOffsetAngle(), 0, Constants.VISION_ROTATION_TOLERANCE);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return inTolerance();
    }

    @Override
    public void end(boolean interrupted) {
        logger.flush();
        drivetrain.stop();
    }
}
