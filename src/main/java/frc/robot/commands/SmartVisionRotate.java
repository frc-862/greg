package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrains.GregDrivetrain;

public class SmartVisionRotate extends ProfiledPIDCommand {
  private static final double kTurnP = 9;
  private static final double kTurnI = 0;
  private static final double kTurnD = 0;
  private static final double kMaxTurnRateDegPerS = 900;
  private static final double kMaxTurnAccelerationDegPerSSquared = 1000;
  private static final double kTurnRateToleranceDegPerS = 100;

  public SmartVisionRotate(GregDrivetrain drive, Vision vision) {
    super(
        new ProfiledPIDController(kTurnP, kTurnI, kTurnD,
        new TrapezoidProfile.Constraints(kMaxTurnRateDegPerS, kMaxTurnAccelerationDegPerSSquared)),
        // Close loop on heading
        vision::getOffsetAngle,
        // Set reference to target
        0,
        // Pipe output to turn robot
        (output, setpoint) -> drive.setOutput(output, -output),
        // Require the drive
        drive);

    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(Constants.shooterAngleTolerance, kTurnRateToleranceDegPerS);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atGoal();
  }
}