/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.leds;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
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
    private final int saturation = 255;
    private final int vibrance = 50;
    private final ArrayList<Integer[]> phaseLed = new ArrayList<>();
    private final Integer[] ledStrip = new Integer[]{1,2};
    private final Integer[] phase0 = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
    private final Integer[] phase1 = new Integer[]{1,2};
    private final Integer[] phase2 = new Integer[]{3,4,5};
    private final Integer[] phase3 = new Integer[]{6,7,8};
    private final Integer[] phase4 = new Integer[]{9,10,11,12};
    private final Integer[] phase5 = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
    private final Integer[] phaseColors = new Integer[]{0, 0, 15, 30, 50, 65};
    public final AddressableLEDMatrix matrix = new AddressableLEDMatrix(3, stripLength, ledPort);
    // DO NOT instantiate AddressableLED or AddressableLEDMatrix more than once
    // ~~~~~~~~~~~~~~~~~~~~~~~~~Riley is ugly~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public LEDs() {
      phaseLed.add(phase0);
      phaseLed.add(phase1);
      phaseLed.add(phase2);
      phaseLed.add(phase3);
      phaseLed.add(phase4);
      phaseLed.add(phase5);
      phaseLed.add(phaseColors);
    }

    public void start(){
      matrix.start();
    }

    private void addPowerCellPhase(int currentPhase){
      System.out.println("LED ADDED");
      matrix.setColor(ledStrip, phaseLed.get(currentPhase), phaseLed.get(6)[currentPhase], saturation, vibrance, 1);
    }

    private void substractPowerCellPhase(int currentPhase){
      System.out.println("LED SUBED");
      if(currentPhase != 4){ matrix.setColor(ledStrip, phaseLed.get(currentPhase + 1), 0,0,0, 1);}
      else{
        addPowerCellPhase(1);
        addPowerCellPhase(2);
        addPowerCellPhase(3);
        addPowerCellPhase(4);
      }
    }

    public void setCurrentPhase(int currentPhase){
      if(lastPhase < currentPhase){
        addPowerCellPhase(currentPhase);
        lastPhase = currentPhase;
      }
      else if(lastPhase > currentPhase){
        substractPowerCellPhase(currentPhase);
        lastPhase = currentPhase;
      }
    }

    public int getCellPhase(){
      return lastPhase;
    }

    public void setAll(){
      matrix.setAll(255, 100, 50, 1);
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
