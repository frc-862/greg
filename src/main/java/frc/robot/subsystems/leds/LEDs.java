/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.leds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.leds.AddressableLEDMatrix;
import frc.robot.commands.ledcommands.RunLeds;
import frc.robot.robots.EddieContainer;
import frc.robot.robots.GregContainer;

/**
 * Add your docs here.
 */
public class LEDs extends SubsystemBase{
    private int lastPhase = 0;
    private final int ledPort = 9;
    private final int stripLength = 12;
    private final Integer[] ledStrip = new Integer[]{1,2};
    private final Integer[] phase1 = new Integer[]{1,2};
    private final Integer[] phase2 = new Integer[]{3,4,5};
    private final Integer[] phase3 = new Integer[]{6,7,8};
    private final Integer[] phase4 = new Integer[]{9,10,11,12};
    private final Integer[] phase5 = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
    private final Integer[] phase0Color = new Integer[]{0,0,0}; //green
    private final Integer[] phase1Color = new Integer[]{0,255,100}; //red
    private final Integer[] phase2Color = new Integer[]{15,255,100}; //yellow
    private final Integer[] phase3Color = new Integer[]{25,255,100}; //blue
    private final Integer[] phase4Color = new Integer[]{40,255,100}; //orange
    private final Integer[] phase5Color = new Integer[]{60,255,100}; //green
    public final AddressableLEDMatrix matrix = new AddressableLEDMatrix(2, stripLength,  ledPort);
    // DO NOT instantiate AddressableLED or AddressableLEDMatrix more than once

    public LEDs() {
      }

    public void start(){
      matrix.start();
    }

    private void AddPowerCellPhase(int currentPhase){
      switch(currentPhase){
        case 0: matrix.setColor(ledStrip, phase5, 0 ,0,0, 1); break;
        case 1: matrix.setColor(ledStrip, phase1, phase1Color[0], phase1Color[1], phase1Color[2], 1); break;
        case 2: matrix.setColor(ledStrip, phase2, phase2Color[0], phase2Color[1], phase2Color[2], 1); break;
        case 3: matrix.setColor(ledStrip, phase3, phase3Color[0], phase3Color[1], phase3Color[2], 1); break;
        case 4: matrix.setColor(ledStrip, phase4, phase4Color[0], phase4Color[1], phase4Color[2], 1); break;
        case 5: matrix.setColor(ledStrip, phase5, phase5Color[0], phase5Color[1], phase5Color[2], 1); break;
      }
    }

    private void SubstractPowerCellPhase(int currentPhase){
      Integer[] currentPhaseLed = new Integer[]{1};
      switch(currentPhase){
        case 1: currentPhaseLed = phase1; break;
        case 2: currentPhaseLed = phase2; break;
        case 3: currentPhaseLed = phase3; break;
        case 4: currentPhaseLed = phase4; break;
        case 5: currentPhaseLed = phase5; break;
      }
      matrix.setColor(ledStrip, currentPhaseLed, phase0Color[0], phase0Color[1],phase0Color[2], 1);
    }

    public void setCurrentPhase(int currentPhase){
      if(lastPhase < currentPhase){
        lastPhase = currentPhase;
        AddPowerCellPhase(currentPhase);
      }
      else if(lastPhase > currentPhase){
        lastPhase = currentPhase;
        SubstractPowerCellPhase(currentPhase);
      }
    }

    public void setAll(){
     // matrix.setAll(100, 0, 255, 1);
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
      matrix.stop();
    }

}
