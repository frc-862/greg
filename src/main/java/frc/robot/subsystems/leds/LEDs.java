/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.leds;

import java.util.ArrayList;

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
    private final int ledPort = 9;
    private final int stripLength = 12;
    private final Integer[] phase1 = new Integer[]{1,2};
    private final Integer[] phase2 = new Integer[]{3,4,5};
    private final Integer[] phase3 = new Integer[]{6,7,8};
    private final Integer[] phase4 = new Integer[]{9,10,11,12};
    private final Integer[] phase5 = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
    private final Integer[] phase1Color = new Integer[]{};
    private final Integer[] phase2Color = new Integer[]{};
    private final Integer[] phase3Color = new Integer[]{};
    private final Integer[] phase4Color = new Integer[]{};
    private final Integer[] phase5Color = new Integer[]{};
    public final AddressableLEDMatrix matrix = new AddressableLEDMatrix(1, stripLength, 1, stripLength, ledPort);
    // DO NOT instantiate AddressableLED or AddressableLEDMatrix more than once

    public LEDs() {

      }
    public void start(){
      matrix.start();
    }

    public void TogglePhase(int ledPhase){
      switch(ledPhase){
        case 1: matrix.setColor(1, phase1, 100, phase1Color[0], phase1Color[1], phase1Color[2]);
        case 2: matrix.setColor(1, phase2, 100, phase2Color[0], phase2Color[1], phase2Color[2]);
        case 3: matrix.setColor(1, phase3, 100, phase3Color[0], phase3Color[1], phase3Color[2]);
        case 4: matrix.setColor(1, phase4, 100, phase4Color[0], phase4Color[1], phase4Color[2]);
        case 5: matrix.setColor(1, phase5, 100, phase5Color[0], phase5Color[1], phase5Color[2]);
      }
    }

    public void setSquareWidth(int squarewidth) {
     // matrix.setSquareMap(squarewidth);
    }

    public void clearMatrix(int square) {
      setSquareWidth(8);
      //matrix.setMap(AddressableLEDMatrix.mapQuad(8, 8), 1, square, 0, 0, 0);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 0, 0, 0);
    }

    public void greenMatrix(int square) {
      setSquareWidth(8);
      //matrix.setMap(AddressableLEDMatrix.mapQuad(8, 8), 1, square, 60, 255, 10);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 60, 255, 10);
    }

    public void yellowMatrix(int square) {
      setSquareWidth(8);
      //matrix.setMap(AddressableLEDMatrix.mapQuad(8, 8), 1, square, 30, 255, 10);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 30, 255, 10);
    }

    public void redMatrix(int square) {
      setSquareWidth(8);
      //matrix.setMap(AddressableLEDMatrix.mapQuad(8, 8), 1, square, 0, 255, 10);
     // matrix.setMap(AddressableLEDMatrix.mapSquare, 1, square, 0, 255, 10);
    }
    
    public void stop(){
      
    }

}
