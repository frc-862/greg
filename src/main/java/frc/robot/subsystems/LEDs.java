/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.util.AddressableLEDMatrix;

/**
 * Add your docs here.
 */
public class LEDs extends SubsystemBase{
    private final int ledCount = 256;
   // private final AddressableLED led;
    private final AddressableLEDBuffer buffer;
    private final AddressableLEDMatrix matrix;
    public LEDs() {
        buffer= new AddressableLEDBuffer(ledCount);
        matrix = new AddressableLEDMatrix(8, 32, 9);
    }

    int pos = 0;
    int hue = 0;
    public boolean toggle = false;
    public static boolean toggleA = false;
    public static boolean toggleB = false;
    public static boolean toggleC = false;
    public static boolean toggleD = false;


    public void start(){
      matrix.start();
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
        buffer.setHSV(pos, hue, 255, 50);

        hue = (hue + 2) % 255;
        pos = (pos + 1) % ledCount;
        if (pos == ledCount) {
            stop();
        }
    }

    @SuppressWarnings("unchecked")
    public void setA(){
      //matrix.setMap(AddressableLEDMatrix.mapSquare,1, 1, 120, 255, 50);
      matrix.setWord("i", 1, 1, 100,255,50);
    }
    public void setB(){
      matrix.setSquareMap(8);
      matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 9, 10, 255, 50);
    }
    public void setC(){
      matrix.setSquareMap(8);
      matrix.setMap(AddressableLEDMatrix.mapSquare,1, 17, 130, 255, 50);
    }
    public void setD(){
      matrix.setSquareMap(8);
      matrix.setMap(AddressableLEDMatrix.mapSquare, 1, 25, 180, 255, 50);
    }

    public void stop(){
        for (int i = 0; i < ledCount; i++){
            buffer.setLED(i, new Color(0,0,0));
        }
        pos = 0;
        hue = 0;
        matrix.stop();
    }

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
      }
}
