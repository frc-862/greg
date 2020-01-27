/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.hal.sim.AddressableLEDSim;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class LED extends SubsystemBase{
    private final int ledCount = 10;
    private final AddressableLED led;
    private final AddressableLEDSim ledSim;
    private final AddressableLEDBuffer buffer;
    public LED() {
        led = new AddressableLED(0);
        ledSim = new AddressableLEDSim(0);
        led.setLength(ledCount);
        buffer= new AddressableLEDBuffer(ledCount);
        led.setData(buffer);
        led.start();
    }

    //int r = 0; //red value
    int pos = 0;
    

    public void cycle() {
        //buffer.setRGB(0, 255, 255, 255);
        buffer.setLED(0, new Color(255, 255, 255));
        led.setData(buffer);
        //r = (r + 1) % 255;
        //SmartDashboard.putNumber("pos", pos);
        //SmartDashboard.putRaw("data", ledSim.getData());
        //pos = (pos + 1) % ledCount;
        //led.start();
        

    }
    public void end(){
        led.stop();
    }
}
