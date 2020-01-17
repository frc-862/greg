/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ctrlPanelOperator;

import java.sql.Time;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CtrlPanelOperator;

public class Spin extends CommandBase {

  CtrlPanelOperator ctrlPanelOperator;
  DoubleSupplier pwr;
  DoubleSupplier time;


  /**
   * Creates a new Spin.
   */
  public void Spin(CtrlPanelOperator ctrlPanelOperator, DoubleSupplier pwr, DoubleSupplier time) {

    addRequirements(ctrlPanelOperator);
    this.pwr = pwr;
    this.time = time;
    

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    this.ctrlPanelOperator = new CtrlPanelOperator();

    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    ctrlPanelOperator.spin(pwr.getAsDouble(), time.getAsDouble());
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    super.end(interrupted);
    ctrlPanelOperator.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return false;

  }
}
