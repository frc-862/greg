/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.ledcommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.leds.AddressableLEDMatrix;
import frc.robot.subsystems.leds.LEDMatrixMap;
import frc.robot.subsystems.leds.LEDs;

public class Pong extends CommandBase {

  int BallDirection = 3;
  int ballColumn = 16;
  int ballRow = 4;
  int leftPaddleHeight = 0;
  int rightPaddleHeight = 0;
  int oldLeftPaddle = 0;
  int oldRightPaddle = 0;
  int playerWin = 0;
  int player1Score = 0;
  int player2Score = 0;
  int gameInProgress= 0;
  double pongSpeed = 0.15;
  //boolean freezePong = false;
  int gameRunning = 0;
  XboxController controller = new XboxController(0);
  XboxController controllertwo = new XboxController (1);

  LEDs leds;
  LEDMatrixMap ledMatrixMap;
  //AddressableLEDMatrix addressableLEDMatrix;
  /**
   * Creates a new Pong.
   */
  public Pong(LEDs leds, LEDMatrixMap ledMatrixMap) {
    
    this.leds = leds;
    this.ledMatrixMap = ledMatrixMap;
    //this.addressableLEDMatrix = addressableLEDMatrix;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(leds, ledMatrixMap);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!(gameInProgress == 1)) {

      BallDirection = (int) (Math.random())*3;
      ballColumn = 16;
      ballRow = 4;
      leftPaddleHeight = 0;
      rightPaddleHeight = 0;
      oldLeftPaddle = 0;
      oldRightPaddle = 0;
      playerWin = 0;
      pongSpeed = 0.15;
      gameRunning = 0;

      player1Score = 0;
      player2Score = 0;

      
      while (gameRunning == 0){

        if (BallDirection==0){

          if (ballColumn == 28 && ballRow == 0){

            if (rightPaddleHeight == 0){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              ballColumn = ballColumn - 1;
              ballRow = ballRow + 1;
              BallDirection = 2; 
            } else {

              player1Score = player1Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn + 1;
              playerWin = 2;
              
            }
          } else if (ballColumn == 28) {

            if (rightPaddleHeight == (ballRow) || rightPaddleHeight == (ballRow-1) || rightPaddleHeight == (ballRow-2)){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              ballColumn = ballColumn - 1;
              ballRow = ballRow - 1;
              BallDirection = 3;
            } else {

              player1Score = player1Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn + 1;
              playerWin = 2;
              
            }
          } else if (ballRow == 0){

            ballRow = ballRow + 1;
            ballColumn = ballColumn + 1;
            BallDirection = 1;
          } else {

            ballRow = ballRow - 1;
            ballColumn = ballColumn + 1;
          }
    
        } else if (BallDirection==1){

          if (ballColumn == 28 && ballRow == 7){

            if (rightPaddleHeight == 5){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              ballColumn = ballColumn - 1;
              ballRow = ballRow - 1;
              BallDirection = 3;
            } else {

              player1Score = player1Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn + 1;
              playerWin = 2;
              
            }
          } else if (ballColumn == 28) {

            if (rightPaddleHeight == (ballRow) || rightPaddleHeight == (ballRow-1) || rightPaddleHeight == (ballRow-2)){
              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              BallDirection = 2;
              ballColumn = ballColumn - 1;
              ballRow = ballRow + 1;
            } else {

              player1Score = player1Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn + 1;
              playerWin = 2;
            }
          } else if (ballRow == 7){

            BallDirection = 0;
            ballRow = ballRow - 1;
            ballColumn = ballColumn + 1;
          } else {

            ballRow = ballRow + 1;
            ballColumn = ballColumn + 1;
          }
    
        } else if (BallDirection==2){

          if (ballColumn == 3 && ballRow == 7){
            if (leftPaddleHeight == 5){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              ballColumn = ballColumn + 1;
              ballRow = ballRow - 1;
              BallDirection = 0;
            } else {

              player2Score = player2Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn - 1;
              playerWin = 1;
            }
          } else if (ballColumn == 3) {

            if (leftPaddleHeight == (ballRow) || leftPaddleHeight == (ballRow-1) || leftPaddleHeight == (ballRow-2)){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              BallDirection = 1;
              ballColumn = ballColumn + 1;
              ballRow = ballRow + 1;
            } else {

              player2Score = player2Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn - 1;
              playerWin = 1;
            }
          } else if (ballRow == 7){

            BallDirection = 3;
            ballColumn = ballColumn - 1;
            ballRow = ballRow - 1;
          } else {

            ballColumn = ballColumn - 1;
            ballRow = ballRow + 1;
          }
    
        } else if (BallDirection == 3){

          if (ballColumn == 3 && ballRow == 0){

            if (leftPaddleHeight == 0){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              ballColumn = ballColumn + 1;
              ballRow = ballRow + 1;
              BallDirection = 1;
            } else {

              player2Score = player2Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn - 1;
              playerWin = 1;
            }
          } else if (ballColumn == 3) {

            if (leftPaddleHeight == (ballRow) || leftPaddleHeight == (ballRow-1) || leftPaddleHeight == (ballRow-2)){

              if (pongSpeed == 0.05){

              } else {

                pongSpeed = pongSpeed - 0.01;
              }
              BallDirection = 0;
              ballColumn = ballColumn + 1;
              ballRow = ballRow - 1;
            } else {

              player2Score = player2Score + 1;
              gameRunning = 1;
              ballColumn = ballColumn - 1;
              playerWin = 1;
            }
          } else if (ballRow == 0){

            BallDirection = 2;
            ballColumn = ballColumn - 1;
            ballRow = ballRow + 1;
          } else {

            ballColumn = ballColumn - 1;
            ballRow = ballRow - 1;
          }
        }
        leds.matrix.setMap(LEDMatrixMap.mapPixel, ballRow , ballColumn, 100, 100, 100);
        
        Timer.delay(pongSpeed);
        //TODO: remove Delay script

        leds.matrix.setMap(LEDMatrixMap.mapPixel, ballRow, ballColumn, 0, 0, 0);
    
    
        oldLeftPaddle = leftPaddleHeight;
        oldRightPaddle = rightPaddleHeight;
    
        double doubleLeftPaddleHeight = controller.getY(Hand.kLeft);
        leftPaddleHeight = (int) ((doubleLeftPaddleHeight + 1) * 2.5);
    
        if (oldLeftPaddle == leftPaddleHeight){
        } else {
          leds.matrix.setMap(LEDMatrixMap.mapPaddle, oldLeftPaddle, 2, 0, 0, 0);
        };
    
        double doubleRightPaddleHeight = controllertwo.getY(Hand.kLeft);
        rightPaddleHeight = (int) ((doubleRightPaddleHeight + 1) * 2.5);
    
        if (oldRightPaddle == rightPaddleHeight){
        } else {
          leds.matrix.setMap(LEDMatrixMap.mapPaddle, oldRightPaddle, 29, 0, 0, 0);
        };
        
        leds.matrix.setMap(LEDMatrixMap.mapPixel, ballRow , ballColumn, 0, 0, 0);

      }
      //matrix.setMap(AddressableLEDMatrix.mapSquare,1, 1, 120, 255, 50);
      //matrix.setWord("i", 1, 1, 100,255,50);
      //matrix.setMap(LEDMatrixMap.mapB, 1, 1, 10, 255, 50);
      //matrix.setMap(LEDMatrixMap.mapR, 1, 7, 10, 255, 50);
      //matrix.setMap(LEDMatrixMap.mapU, 1, 13, 10, 255, 50);
      //matrix.setMap(LEDMatrixMap.mapH, 1, 19, 10, 255, 50);
      //matrix.setMap(LEDMatrixMap.mapExclamation, 1, 25, 7, 255, 50);
      //matrix.setSquareMap(8);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 1, 60, 255, 10);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 9, 30, 255, 10);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 17, 0, 255, 10);

      //if ((Timer.getFPGATimestamp() - timerAtor) >= 1.0) { 
        //if (toggleForTimer) {
          //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 60, 255, 100);
          //toggleForTimer = false;
        //}
        //else {
          //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 0, 0, 0);
          //toggleForTimer = true;
        //}
        //timerAtor = Timer.getFPGATimestamp();
      //}
      //best green = RGB(0, 255, 0), HSV(60, 255, 100)
      //best yellow = RGB(255, 128, 0), HSV(30, 255, 100)
      //matrix.setColor(1, 1, 0, 255, 0);
      //matrix.setColor(1, 2, 255, 128, 0);
     leds.matrix.setMap(LEDMatrixMap.mapPixel, ballRow, ballColumn-1, 100, 100, 100);
      Timer.delay(0.05);
     leds.matrix.setMap(LEDMatrixMap.mapPixel, ballRow, ballColumn-1, 0, 0, 0);
      Timer.delay(0.05);
      //Delete this: ^
      //             |
      //matrix.clearColor();
      if (playerWin == 2){
       leds.matrix.setMap(LEDMatrixMap.mapPaddle, oldLeftPaddle, 2, 0, 0, 0);
       leds.matrix.setMap(LEDMatrixMap.mapPaddle, oldRightPaddle, 29, 0, 0, 0);
       leds.matrix.setMap(LEDMatrixMap.mapP, 0, 0, 255, 0, 0);
       leds.matrix.setMap(LEDMatrixMap.map1, 0, 6, 255, 0, 0);
        if (player1Score < 5){
          switch(player1Score){
            case 0:leds.matrix.setMap(LEDMatrixMap.map0, 0, 24, 100, 100, 100);
            case 1:leds.matrix.setMap(LEDMatrixMap.map1, 0, 24, 100, 100, 100);
            case 2:leds.matrix.setMap(LEDMatrixMap.map2, 0, 24, 100, 100, 100);
            case 3:leds.matrix.setMap(LEDMatrixMap.map3, 0, 24, 100, 100, 100);
            case 4:leds.matrix.setMap(LEDMatrixMap.map4, 0, 24, 100, 100, 100);
          }
        } else {
          gameInProgress = 0;
        }
      } else {
       leds.matrix.setMap(LEDMatrixMap.mapPaddle, oldLeftPaddle, 2, 0, 0, 0);
       leds.matrix.setMap(LEDMatrixMap.mapPaddle, oldRightPaddle, 29, 0, 0, 0);
       leds.matrix.setMap(LEDMatrixMap.mapP, 0, 0, 0, 0, 255);
       leds.matrix.setMap(LEDMatrixMap.map2, 0, 6, 0, 0, 255);
        if (player2Score < 5){
          switch(player2Score){
            case 0:leds.matrix.setMap(AddressableLEDMatrix.map0, 0, 24, 100, 100, 100);
            case 1:leds.matrix.setMap(AddressableLEDMatrix.map1, 0, 24, 100, 100, 100);
            case 2:leds.matrix.setMap(AddressableLEDMatrix.map2, 0, 24, 100, 100, 100);
            case 3:leds.matrix.setMap(AddressableLEDMatrix.map3, 0, 24, 100, 100, 100);
            case 4:leds.matrix.setMap(AddressableLEDMatrix.map4, 0, 24, 100, 100, 100);
          }
        } else {
          gameInProgress = 0;
        }
      }

      
      Timer.delay(3);


      if (playerWin == 2){
         leds.matrix.setMap(LEDMatrixMap.mapP, 0, 0, 0, 0, 0);
         leds.matrix.setMap(LEDMatrixMap.map1, 0, 6, 0, 0, 0);
          switch(player1Score){
            case 0:leds.matrix.setMap(AddressableLEDMatrix.map0, 0, 24, 0, 0, 0);
            case 1:leds.matrix.setMap(AddressableLEDMatrix.map1, 0, 24, 0, 0, 0);
            case 2:leds.matrix.setMap(AddressableLEDMatrix.map2, 0, 24, 0, 0, 0);
            case 3:leds.matrix.setMap(AddressableLEDMatrix.map3, 0, 24, 0, 0, 0);
            case 4:leds.matrix.setMap(AddressableLEDMatrix.map4, 0, 24, 0, 0, 0);
          }
      } else {
         leds.matrix.setMap(LEDMatrixMap.mapP, 0, 0, 0, 0, 0);
         leds.matrix.setMap(LEDMatrixMap.map2, 0, 6, 0, 0, 0);
          switch(player1Score){
            case 0:leds.matrix.setMap(AddressableLEDMatrix.map0, 0, 24, 0, 0, 0);
            case 1:leds.matrix.setMap(AddressableLEDMatrix.map1, 0, 24, 0, 0, 0);
            case 2:leds.matrix.setMap(AddressableLEDMatrix.map2, 0, 24, 0, 0, 0);
            case 3:leds.matrix.setMap(AddressableLEDMatrix.map3, 0, 24, 0, 0, 0);
            case 4:leds.matrix.setMap(AddressableLEDMatrix.map4, 0, 24, 0, 0, 0);
          }
      }
     leds.matrix.setMap(LEDMatrixMap.mapH, 0, 0, 100, 100, 100);
     leds.matrix.setMap(LEDMatrixMap.mapO, 0, 6, 100, 100, 100);
     leds.matrix.setMap(LEDMatrixMap.mapL, 0, 12, 100, 100, 100);
     leds.matrix.setMap(LEDMatrixMap.mapD, 0, 18, 100, 100, 100);
     leds.matrix.setMap(LEDMatrixMap.mapA, 0, 25, 0, 255, 0);
      while (controller.getAButton() == false /*|| controllertwo.getAButton() == false*/){
        Timer.delay(0.5);
      }
     leds.matrix.setMap(LEDMatrixMap.mapH, 0, 0, 0, 0, 0);
     leds.matrix.setMap(LEDMatrixMap.mapO, 0, 6, 0, 0, 0);
     leds.matrix.setMap(LEDMatrixMap.mapL, 0, 12, 0, 0, 0);
     leds.matrix.setMap(LEDMatrixMap.mapD, 0, 18, 0, 0, 0);
     leds.matrix.setMap(LEDMatrixMap.mapA, 0, 25, 0, 0, 0);
    }
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
