/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import static org.mockito.Mockito.*;

import org.junit.Test;

import frc.robot.commands.ledcommands.SolidGreen;
import frc.robot.commands.ledcommands.SolidRed;
import frc.robot.commands.ledcommands.SolidYellow;
import frc.robot.subsystems.leds.LEDs;

/**
 * Add your docs here.
 */
public class LedsTest {
    
    @Test
    public void square1(){
        LEDs leds = mock(LEDs.class);
        SolidGreen solidGreen = new SolidGreen(() -> 1, leds);
        SolidYellow solidYellow = new SolidYellow(() -> 1, leds);
        SolidRed solidRed = new SolidRed(() -> 1, leds);

        solidGreen.execute();

        verify(leds, never()).stop();
        verify(leds).greenMatrix(1);

        solidYellow.execute();

        verify(leds, never()).stop();
        verify(leds).yellowMatrix(1);

        solidRed.execute();

        verify(leds, never()).stop();
        verify(leds).redMatrix(1);
    }

    @Test
    public void square2(){
        LEDs leds = mock(LEDs.class);
        SolidGreen solidGreen = new SolidGreen(() -> 2, leds);
        SolidYellow solidYellow = new SolidYellow(() -> 2, leds);
        SolidRed solidRed = new SolidRed(() -> 2, leds);

        solidGreen.execute();

        verify(leds, never()).stop();
        verify(leds).greenMatrix(2);

        solidYellow.execute();

        verify(leds, never()).stop();
        verify(leds).yellowMatrix(2);

        solidRed.execute();

        verify(leds, never()).stop();
        verify(leds).redMatrix(2);
    }

    @Test
    public void square3(){
        LEDs leds = mock(LEDs.class);
        SolidGreen solidGreen = new SolidGreen(() -> 3, leds);
        SolidYellow solidYellow = new SolidYellow(() -> 3, leds);
        SolidRed solidRed = new SolidRed(() -> 3, leds);

        solidGreen.execute();

        verify(leds, never()).stop();
        verify(leds).greenMatrix(3);

        solidYellow.execute();

        verify(leds, never()).stop();
        verify(leds).yellowMatrix(3);

        solidRed.execute();

        verify(leds, never()).stop();
        verify(leds).redMatrix(3);
    }

    
    @Test
    public void square4(){
        LEDs leds = mock(LEDs.class);
        SolidGreen solidGreen = new SolidGreen(() -> 4, leds);
        SolidYellow solidYellow = new SolidYellow(() -> 4, leds);
        SolidRed solidRed = new SolidRed(() -> 4, leds);

        solidGreen.execute();

        verify(leds, never()).stop();
        verify(leds).greenMatrix(4);

        solidYellow.execute();

        verify(leds, never()).stop();
        verify(leds).yellowMatrix(4);

        solidRed.execute();

        verify(leds, never()).stop();
        verify(leds).redMatrix(4);
    }

    @Test
    public void greenStopAtEnd(){
        LEDs leds = mock(LEDs.class);
        SolidGreen solidGreen = new SolidGreen(() -> 1, leds);

        solidGreen.end(false);
        verify(leds).stop();
    }

    @Test
    public void yellowStopAtEnd(){
        LEDs leds = mock(LEDs.class);
        SolidYellow solidYellow = new SolidYellow(() -> 1, leds);

        solidYellow.end(false);
        verify(leds).stop();
    }

    @Test
    public void redStopAtEnd(){
        LEDs leds = mock(LEDs.class);
        SolidRed solidRed = new SolidRed(() -> 1, leds);

        solidRed.end(false);
        verify(leds).stop();
    }
}
