/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.leds.LEDs;


public class RunLeds extends CommandBase {
  /**
   * Creates a new RunLeds.
   */
  private final LEDs leds;
  boolean down= false;
  boolean up = true;
  //private final Indexer indexer;
  //private final Shooter shooter;
  private int currentPhase = 4;
  public RunLeds(LEDs leds /*, Indexer indexer, Shooter shooter*/) {
    //this.indexer = indexer;
    //this.shooter = shooter;
    this.leds = leds;
    addRequirements(leds);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //init codes
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //currentPhase = indexer.getBallCount() - shooter.getBallsFired();
    if(down && currentPhase > 0){currentPhase --;}
    else if(currentPhase <= 0){up = true; down = false;}
    if(up && currentPhase < 5){currentPhase ++;}
    else if (currentPhase >= 5){up = false; down = true;}
    leds.setCurrentPhase(currentPhase);
    Timer.delay(1);
    //currentPhase ++;
    //leds.setCurrentPhase(currentPhase);
    //Timer.delay(1);
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leds.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
