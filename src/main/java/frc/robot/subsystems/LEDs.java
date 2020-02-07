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

/**
 * Add your docs here.
 */
public class LEDs extends SubsystemBase{
    private final int ledCount = 256;
    private final AddressableLED led;
    private final AddressableLEDBuffer buffer;
    
    public LEDs() {
        led = new AddressableLED(9);
        led.setLength(ledCount);
        buffer= new AddressableLEDBuffer(ledCount);

        clearBuffer();
        led.setData(buffer);
        led.start();
    }

    int pos = 0;
    int hue = 0;
    public boolean toggle = false;


    public void setLED2Buffer() {
        led.setData(buffer);
    }

    public void updateBuffer(int r, int g, int b) {
        for(var i = 0 ; i < ledCount ; ++i) buffer.setRGB(i, r, g, b);
    }

    public void clearBuffer() {
        for (int i = 0; i < ledCount; i++) buffer.setLED(i, new Color(0, 0, 0));
    }

    public void red() {
        for (int i = 0; i < ledCount; i++) buffer.setRGB(i, 50, 0, 0);
    }

    public void green() {
        for (int i = 0; i < ledCount; i++) buffer.setRGB(i, 0, 50, 0);
    }

    public void blue() {
        for (int i = 0; i < ledCount; i++) buffer.setRGB(i, 0, 0, 50);
    }

    public void Toggle(){
        if (toggle){
            toggle = false;
        }
        else { 
            toggle = true;
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
    public void stop(){
        for (int i = 0; i < ledCount; i++){
            buffer.setLED(i, new Color(0,0,0));
        }
        pos = 0;
        hue = 0;
        led.setData(buffer);
    }
}
