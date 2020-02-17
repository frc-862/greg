/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.leds.AddressableLEDMatrix;
import frc.robot.subsystems.leds.LEDMatrixMap;
import frc.robot.subsystems.leds.LEDs;


public class RunLeds extends CommandBase {
  /**
   * Creates a new RunLeds.
   */
  LEDs leds;
  public int ballColumn = 16;
  public int ballRow = 5;
  
  int BallDirection = 0;
  boolean gameRunning = true;



  public RunLeds(LEDs leds) {
    this.leds = leds;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //init codes
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
      //if(EddieContainer.Leds.toggleA){EddieContainer.Leds.setA(); }
      //else if(!EddieContainer.Leds.toggleA){EddieContainer.Leds.clearA();}

      //if(EddieContainer.Leds.toggleB){EddieContainer.Leds.setB(); }
      //else if(!EddieContainer.Leds.toggleB){EddieContainer.Leds.clearB();}

      //if(EddieContainer.Leds.toggleC){EddieContainer.Leds.setC();}
      //else if(!EddieContainer.Leds.toggleC){EddieContainer.Leds.clearC();}

      //if(EddieContainer.Leds.toggleD){EddieContainer.Leds.setD();}
      //else if(!EddieContainer.Leds.toggleD){EddieContainer.Leds.clearD();}
      //EddieContainer.Leds.rainbow();
      /*
      while (gameRunning){
        if (BallDirection==0){
          if (ballColumn == 31 && ballRow == 0){
            ballColumn = ballColumn - 1;
            ballRow = ballRow + 1;
            BallDirection = 2;
          } else if (ballColumn == 31) {
            ballColumn = ballColumn - 1;
            ballRow = ballRow - 1;
            BallDirection = 3;
          } else if (ballRow == 0){
            ballRow = ballRow + 1;
            ballColumn = ballColumn + 1;
            BallDirection = 1;
          } else {
            ballRow = ballRow - 1;
            ballColumn = ballColumn + 1;
          }
        } else if (BallDirection==1){
          if (ballColumn == 31 && ballRow == 7){
            ballColumn = ballColumn - 1;
            ballRow = ballRow - 1;
            BallDirection = 3;
          } else if (ballColumn == 31) {
            BallDirection = 2;
            ballColumn = ballColumn - 1;
            ballRow = ballRow + 1;
          } else if (ballRow == 7){
            BallDirection = 0;
            ballRow = ballRow - 1;
            ballColumn = ballColumn + 1;
          } else {
            ballRow = ballRow + 1;
            ballColumn = ballColumn + 1;
          }
        } else if (BallDirection==2){
          if (ballColumn == 0 && ballRow == 7){
            ballColumn = ballColumn + 1;
            ballRow = ballRow - 1;
            BallDirection = 0;
          } else if (ballColumn == 0) {
            BallDirection = 1;
            ballColumn = ballColumn + 1;
            ballRow = ballRow + 1;
          } else if (ballRow == 7){
            BallDirection = 3;
            ballColumn = ballColumn - 1;
            ballRow = ballRow - 1;
          } else {
            ballColumn = ballColumn - 1;
            ballRow = ballRow + 1;
          }
        } else if (BallDirection == 3){
          if (ballColumn == 0 && ballRow == 0){
            ballColumn = ballColumn + 1;
            ballRow = ballRow + 1;
            BallDirection = 1;
          } else if (ballColumn == 0) {
            BallDirection = 0;
            ballColumn = ballColumn + 1;
            ballRow = ballRow - 1;
          } else if (ballRow == 0){
            BallDirection = 2;
            ballColumn = ballColumn - 1;
            ballRow = ballRow + 1;
          } else {
            ballColumn = ballColumn - 1;
            ballRow = ballRow - 1;
          }
        }
        //System.out.println("The ball should move");
        Timer.delay(0.5);
        leds.setA(); 
      }*/
      leds.setA();
    }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leds.stop();
    //LEDs.clearBuffer();
    //LEDs.setLED2Buffer();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
