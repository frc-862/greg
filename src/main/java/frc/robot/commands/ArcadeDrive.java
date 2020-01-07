/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

/**
 * An example command that uses an example subsystem.
 */
public class ArcadeDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private final DoubleSupplier throttle;
  private final DoubleSupplier turn;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ArcadeDrive(Drivetrain subsystem, DoubleSupplier throttle, DoubleSupplier turn) {
    drivetrain = subsystem;
    this.throttle = throttle;
    this.turn = turn;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double speed = throttle.getAsDouble();
    final double rotation = turn.getAsDouble();
    final boolean quickTurn = Math.abs(speed) < 0.05 && Math.abs(rotation) > 0.2;
    drivetrain.getDifferentialDrive().curvatureDrive(speed, rotation, quickTurn);
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
