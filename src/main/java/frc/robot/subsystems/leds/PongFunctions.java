/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.leds;
import java.util.Random;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class PongFunctions extends SubsystemBase{

    Random random = new Random();
    public int P1Y = 4;
    public int P2Y = 4;
    public int BallX = 20; //ranges from 1 to 32
    public int BallY = 4; //ranges from 1 to 8
    public boolean Contact1 = false;
    public boolean Contact2 = false;
    public int BallDir = random.nextInt(8);
    public int BallSpeed = 1; //1 PPS
    public int P1Score = 0;
    public int P2Score = 0;

    int[] upDirs    =  {0, 1, 2};
    int[] rightDirs =  {2, 3, 4};
    int[] leftDirs  =  {0, 7, 6};
    int[] downDirs  =  {6, 5, 4};

    public void drawPixel(int x, int y){
    System.out.println("Dreweds X){ " + Integer.toString(x) + " Y){ " + Integer.toString(y));
    //Draw The Pixels Here
    }
    public void clearPixel(int x, int y){
    System.out.println("cleared X){ " + Integer.toString(x) + " Y){ " + Integer.toString(y));
    //Draw The Pixels Here
    }
    public void UpdateBallDir(){
        if (Contact1) {
            BallDir = rightDirs[random.nextInt(3)];
        }
        else if (Contact2){
            BallDir = leftDirs[random.nextInt(3)];
        }
        if (BallY == 8 && (BallDir == 0 || BallDir == 1 || BallDir == 2)){ // up direction
            if (BallDir == 2 || BallDir == 3 || BallDir == 4){ //right direction
                BallDir = 4;
            }
            else if (BallDir == 0 || BallDir == 6 || BallDir == 7){ //left direction
                BallDir = 6;
            }
        }
        else if (BallY == 1 && (BallDir == 6 || BallDir == 5 || BallDir == 4)){ // down direction
            if (BallDir == 2 || BallDir == 3 || BallDir == 4){ //right direction
                BallDir = 2;
            }
            else if (BallDir == 0 || BallDir == 6 || BallDir == 7){ //left direction
                BallDir = 0;
            }
        }
    }
    public void drawPaddle(int player, int y){
        if (player == 1){
            drawPixel(1, y - 1);
            drawPixel(1, y);
            drawPixel(1, y + 1);
        }
        else if (player == 2){
            drawPixel(32, y - 1);
            drawPixel(32, y);
            drawPixel(32, y + 1);
        }
    }
    public void moveBall(int dir, int speed){
        //BallDirs
        // 0  1  2
        // 7     3
        // 6  5  4
        if (dir == 0){
            BallX = BallX - speed;
            BallY = BallY + speed;
        }
        else if (dir == 1){
            BallY = BallY + speed;
        }
        else if (dir == 2){
            BallX = BallX + speed;
            BallY = BallY + speed;
        }
        else if (dir == 3){
            BallX = BallX + speed;
        }
        else if (dir == 4){
            BallX = BallX + speed;
            BallY = BallY - speed;
        }
        else if (dir == 5){
            BallY = BallY - speed;
        }
        else if (dir == 6){
            BallX = BallX - speed;
            BallY = BallY - speed;
        }
        else if (dir == 7){
            BallX = BallX - speed;
        }
    }
    public String dirToEnglish(int dir) {
        //BallDirs
        // 0  1  2
        // 7     3
        // 6  5  4
        if (dir == 0){
            return "Up Left";
        }
        else if (dir == 1){
            return "Up";
        }
        else if (dir == 2){
            return "Up Right";
        }
        else if (dir == 3){
            return "Right";
        }
        else if (dir == 4){
            return "Down Right";
        }
        else if (dir == 5){
            return "Down";
        }
        else if (dir == 6){
            return "Down Left";
        }
        else if (dir == 7){
            return "Left";
        }
        else {
            return "Help Me";
        }
    }
}