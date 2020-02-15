/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.leds;

//import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.leds.AddressableLEDMatrix;

/**
 * Add your docs here.
 */
public class LEDs extends SubsystemBase{
  //private final int ledCount = 256;
  //private final AddressableLED led;
  //private final AddressableLEDBuffer buffer;
  private final AddressableLEDMatrix matrix;
  public LEDs() {
    //buffer= new AddressableLEDBuffer(ledCount);
    matrix = new AddressableLEDMatrix(8, 32, 9);
  }

  //int pos = 0;
  //int hue = 0;
  public boolean toggle = false;
  public static boolean toggleA = false;
  public static boolean toggleB = false;
  public static boolean toggleC = false;
  public static boolean toggleD = false;


  public void start(){
    matrix.start();
  }

    /*public void ToggleLED(int LEDSection){
      switch(LEDSection){
        case 1: toggleA = !toggleA; break;
        case 2:toggleB = !toggleB; break;
        case 3:toggleC = !toggleC; break;
        case 4:toggleD = !toggleD; break;
      }
    }*/


    /*public void rainbow() {
        this function relies on the fact that it is being called constantly
        buffer.setHSV(pos, hue, 255, 50);

        hue = (hue + 2) % 255;
        pos = (pos + 1) % ledCount;
        if (pos == ledCount) {
            stop();
        }
    }*/

  public void setSquareWidth(int squarewidth) {
    matrix.setSquareMap(squarewidth);
  }

  public void clearMatrix(int square) {
    //setSquareWidth(square);
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 0, 0, 0);
  }

  public void greenMatrix(int square) {
    //setSquareWidth(square);
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 60, 255, 10);
  }

  public void yellowMatrix(int square) {
    //setSquareWidth(square);
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 30, 255, 10);
  }

  public void redMatrix(int square) {
    //setSquareWidth(square);
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 0, 255, 10);
  }

    //@SuppressWarnings("unchecked")
    //public void setA(){
      //best green = RGB(0, 255, 0), HSV(60, 255, 100)
      //best yellow = RGB(255, 128, 0), HSV(30, 255, 100)

    //}
    //public void setB(){
      //matrix.setSquareMap(8);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 9, 120, 255, 50);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 17, 60, 255, 50);
    //}
    //public void setC(){
      //matrix.setSquareMap(8);
      //matrix.setMap(AddressableLEDMatrix.mapSquare,1, 17, 130, 255, 50);
    //}
    //public void setD(){
      //matrix.setSquareMap(8);
      //matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 180, 255, 50);
    //}

  public void stop(){
    System.out.println("The End Has Begun");
    //for (int i = 0; i < ledCount; i++){
    //    buffer.setLED(i, new Color(0,0,0));
    //}
    //pos = 0;
    //hue = 0;
    clearMatrix(1);
    clearMatrix(2);
    clearMatrix(3);
    clearMatrix(4);
    //matrix.stop();
  }
  /*
  public void clearA(){
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 1, 150, 0, 0);
  }

  public void clearB(){
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 9, 150, 0, 0);
  }

  public void clearC(){
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 17, 150, 0, 0);
  }

  public void clearD(){
    matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 150, 0, 0);
  }*/
}
