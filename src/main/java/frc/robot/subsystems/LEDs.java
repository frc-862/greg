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
    private final int ledCount = 150;
    private final AddressableLED led;
    //private final AddressableLEDSim ledSim;
    private final AddressableLEDBuffer buffer;
    public LEDs() {
        led = new AddressableLED(0);
        //ledSim = new AddressableLEDSim(0);
        led.setLength(ledCount);
        buffer= new AddressableLEDBuffer(ledCount);
        led.setData(buffer);
        led.start();
    }

    //int r = 0; //red value
    int pos = 0;
    int hue = 0;
    

    public void cycle() {
        led.start();
        //buffer.setRGB(0, 255, 255, 255);
        //buffer.setRGB(1, 255, 0, 0);
        //if(RobotContainer.m_driverController.getAButtonPressed()) {RobotContainer.up = true;}
        //else if (RobotContainer.m_driverController.getBButtonPressed()) {RobotContainer.up = false;}
        //if(RobotContainer.up){
            //for (int i = 0; i < ledCount; i++) {
            //buffer.setHSV(i, i, 255, 255);
            //}
            //led.setData(buffer);
        //}
        //if(!RobotContainer.up){
            //for (int i = 0; i < ledCount; i++) {
                //buffer.setHSV(i, 180-i, 255, 255);
                //}
        //}
        buffer.setHSV(pos, hue, 255, 200);
        led.setData(buffer);

        hue = (hue + 1) % 255;
        pos = (pos + 1) % ledCount;
        //System.out.println(RobotContainer.up);
        System.out.println("Cycle has been run.");
        //buffer.setRGB(100, 0, 0, 255);
        led.setData(buffer);
        //r = (r + 1) % 255;
        //SmartDashboard.putNumber("pos", pos);
        //SmartDashboard.putRaw("data", ledSim.getData());
        //pos = (pos + 1) % ledCount;
        

    }
    public void end(){
        pos = 0;
        for (int i = 0; i < ledCount; i++){
            buffer.setLED(i, new Color(0,0,0));
            //led.setData(buffer);
            //System.out.println(i);
        }
        led.setData(buffer);
        System.out.println("The end has begun.");
        led.stop();
    }
}
