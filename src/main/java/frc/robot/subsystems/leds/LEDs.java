/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.leds.AddressableLEDMatrix;
import frc.robot.commands.ledcommands.RunLeds;
import frc.robot.robots.EddieContainer;

/**
 * Add your docs here.
 */
public class LEDs extends SubsystemBase{

    private final int ledCount = 256;
   // private final AddressableLED led;
    //private final AddressableLEDBuffer buffer;
    public final AddressableLEDMatrix matrix;
    public LEDs() {
        //buffer= new AddressableLEDBuffer(256);
        matrix = new AddressableLEDMatrix(8, 32, 1, 150, 9);
      }


    int pos = 0;
    int hue = 0;
    public boolean toggle = false;
    public static boolean toggleA = false;
    public static boolean toggleB = false;
    public static boolean toggleC = false;
    public static boolean toggleD = false;
    boolean toggleForTimer = true;
    double timerAtor;


    public void start(){
     // matrix.start();
    }

    public void Toggle(){
        if (toggle){
            toggle = false;
        }
        else { 
            toggle = true;
        }
    }

    public void ToggleLED(int LEDSection){
      switch(LEDSection){
        case 1: toggleA = !toggleA; break;
        case 2:toggleB = !toggleB; break;
        case 3:toggleC = !toggleC; break;
        case 4:toggleD = !toggleD; break;
      }
    }


    public void rainbow() {
        //this function relies on the fact that it is being called constantly
        //buffer.setHSV(pos, hue, 255, 50);

        hue = (hue + 2) % 255;
        pos = (pos + 1) % ledCount;
        if (pos == ledCount) {
            stop();
        }
    }

    public void setSquareWidth(int squarewidth) {
     // matrix.setSquareMap(squarewidth);
    }

    public void clearMatrix(int square) {
      setSquareWidth(8);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 0, 0, 0);
    }

    public void greenMatrix(int square) {
      setSquareWidth(8);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 60, 255, 10);
    }

    public void yellowMatrix(int square) {
      setSquareWidth(8);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 30, 255, 10);
    }

    public void redMatrix(int square) {
      setSquareWidth(8);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 0, 255, 10);
    }

    //@SuppressWarnings("unchecked")
    public void setA(){

    }
    public void setB(){
      //matrix.setSquareMap(8);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 9, 120, 255, 50);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 17, 60, 255, 50);
    }
    public void setC(){
      //matrix.setSquareMap(8);
     // matrix.setMap(AddressableLEDMatrix.mapSquare,1, 17, 130, 255, 50);
    }
    public void setD(){
     // matrix.setSquareMap(8);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 180, 255, 50);
    }

    public void stop(){
      for (int i = 0; i < ledCount; i++){
          //buffer.setLED(i, new Color(0,0,0));
      }
      pos = 0;
      hue = 0;
     // matrix.stop();
    }

    public void clearA(){
       // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 1, 150, 0, 0);
    }

    public void clearB(){
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 9, 150, 0, 0);
    }

    public void clearC(){
    //  matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 17, 150, 0, 0);
    }

    public void clearD(){
      matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 150, 0, 0);
    }
    
}
