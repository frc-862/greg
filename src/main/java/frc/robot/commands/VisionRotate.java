/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.logging.CommandLogger;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;

public class VisionRotate extends CommandBase {
    private static final double VISION_ROTATE_P = 2 / 100;
    private static final double MIN_ROTATE_PWR = 0.06;
    private static final double VISION_ROTATE_D = 0;

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
        logger.addDataElement("gyro");
    }

    @Override
    public void initialize() {
        vision.bothRingsOn();
    }

    // States to consider
    //   * Lost vision -- continue doing what we were? stop? estimate?
    private double prevOffset = 0;
    private double lastSeen = Timer.getFPGATimestamp();
    private final double max_memory = 0.6;

    @Override
    public void execute() {
        double deltaError = 0;
        double visionOffset;
        double now = Timer.getFPGATimestamp();
        double deltaTime = now - lastSeen;

        if (vision.seePortTarget()) {
            visionOffset = vision.getOffsetAngle();
            deltaError = (prevOffset - visionOffset) / deltaTime;
            prevOffset = visionOffset;
            lastSeen = now;
        } else if (deltaTime <= max_memory){
            visionOffset = prevOffset * ((max_memory - (now - lastSeen)) / max_memory);
            deltaError = (prevOffset - visionOffset) / deltaTime;
        } else {
            visionOffset = 0;
        }

        double pwr = visionOffset * VISION_ROTATE_P - deltaError * VISION_ROTATE_D;
        // TODO investigate cubing P term

        logger.set("error", visionOffset);
        logger.set("power", pwr);
        // TODO implement LightningDriveTrain.getHeading().getDegrees()
       // logger.set("gyro", drivetrain.getHeading().getDegrees());

        if (visionOffset > 0) {
            pwr = LightningMath.constrain(pwr, MIN_ROTATE_PWR, 1);
        }
        if (visionOffset < 0) {
            pwr = LightningMath.constrain(pwr, -1, -MIN_ROTATE_PWR);
        }

        drivetrain.setOutput(12 * pwr, -pwr * 12);
        SmartDashboard.putBoolean("in tolerance", inTolerance());
    }

    private boolean inTolerance() {
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
