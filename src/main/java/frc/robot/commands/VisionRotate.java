/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;

public class VisionRotate extends CommandBase {
    private static final double VISION_ROTATE_P = .15 / 320;
    private static final double MIN_ROTATE_PWR = 0.0125;

    LightningDrivetrain drivetrain;
    Vision vision;
    //private final LightningDrivetrain drivetrain = new QuasarDrivetrain();

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

            double visionOffset = vision.getOffsetAngle()-320;
            double pwr = visionOffset * VISION_ROTATE_P;


            //System.out.println("VR exec: " + pwr);
            //pwr = Math.max(Math.abs(pwr), MIN_ROTATE_PWR);
            if(pwr < MIN_ROTATE_PWR && pwr>0){
                pwr = MIN_ROTATE_PWR;
            }
            if(pwr > -MIN_ROTATE_PWR && pwr <0){
                pwr = -MIN_ROTATE_PWR;
            }
            drivetrain.setPower(pwr, -pwr);
            //right is pos
            //left is neg

        }
    }

    private boolean inTolerance() {
        return vision.seePortTarget() && LightningMath.epsilonEqual(vision.getOffsetAngle(), 0, Constants.ROTATION_TOLERANCE);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return inTolerance();
    }
    
    @Override
    public void end(boolean interrupted){
        drivetrain.stop();
    }
}
