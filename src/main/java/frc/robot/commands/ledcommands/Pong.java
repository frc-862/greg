/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.leds.PongFunctions;
import edu.wpi.first.wpilibj.Timer;

import java.util.function.IntSupplier;

public class Pong extends CommandBase {

  /*
   * /* BallDirs /* 0 1 2 
   * /* 7 3 
   * /* 6 5 4
   */

  /**
   * Creates a new Pong.
   */
  PongFunctions pongFunctions;
  IntSupplier P1Y;
  IntSupplier P2Y;

  public Pong(PongFunctions pongFunctions, IntSupplier P1Y, IntSupplier P2Y) {
    this.pongFunctions = pongFunctions;
    this.P1Y = P1Y;
    this.P2Y = P2Y;
    addRequirements(pongFunctions);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pongFunctions.P1Y = P1Y.getAsInt();
    pongFunctions.P2Y = P2Y.getAsInt();
    pongFunctions.UpdateBallDir();
    pongFunctions.clearPixel(pongFunctions.BallX, pongFunctions.BallY);
    pongFunctions.moveBall(pongFunctions.BallDir, pongFunctions.BallSpeed);
    pongFunctions.drawPixel(pongFunctions.BallX, pongFunctions.BallY);
    if (pongFunctions.BallX == 1){
      pongFunctions.Contact1 = true;
      if (pongFunctions.BallY != pongFunctions.P1Y - 1 || pongFunctions.BallY != pongFunctions.P1Y ||pongFunctions.BallY != pongFunctions.P1Y + 1){
        pongFunctions.P2Score += 1;
      }
    }
    else{
      pongFunctions.Contact1 = false;
    }
    if (pongFunctions.BallX == 32){
      pongFunctions.Contact2 = true;
      if (pongFunctions.BallY != pongFunctions.P2Y - 1 || pongFunctions.BallY != pongFunctions.P2Y ||pongFunctions.BallY != pongFunctions.P2Y + 1){
        pongFunctions.P1Score += 1;
      }
    }
            
    else{
      pongFunctions.Contact2 = false;
    }
    System.out.println("Dir: " + pongFunctions.dirToEnglish(pongFunctions.BallDir));
    System.out.println("X: " + Integer.toString(pongFunctions.BallX) + " Y: " + Integer.toString(pongFunctions.BallY));
    System.out.println("P1: " + Integer.toString(pongFunctions.P1Score) + " P2: " + Integer.toString(pongFunctions.P2Score));
    if (pongFunctions.BallX > 32 || pongFunctions.BallX < 1){
        System.out.println("BallX: " + Integer.toString(pongFunctions.BallX) + " out of Range!");
        end(false);
    }
    else if (pongFunctions.BallY > 32 || pongFunctions.BallY < 1){
        System.out.println("BallY: " + Integer.toString(pongFunctions.BallY) + " out of range!");
        end(false);
    }
    Timer.delay(1.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
