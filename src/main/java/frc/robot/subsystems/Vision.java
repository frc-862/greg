/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    /**
     * Creates a new Vision.
     */
    public Vision() {

    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public double getDistanceFromTarget() {
        return 0d;
    }

    public double getOffsetAngle() {
        return 0d;
    }

    public boolean seePortTarget() {
        return false;
    }

    public static double getTargetFlywheelSpeed() {
        return 0d;
    }

    public boolean seeBayTarget() {
        return false;
    }

    public double getBestShooterAngle() {
        return 0;
    }

    public double getBestShooterVelocity() {
        return 0;
    }
}
