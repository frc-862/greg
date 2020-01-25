package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase {
    private final int ledCount = 150;
    private final AddressableLED led;
    private final AddressableLEDBuffer buffer;

    public LED() {
        led = new AddressableLED(0);
        led.setLength(ledCount);

        buffer = new AddressableLEDBuffer(ledCount);
        led.setData(buffer);
        led.start();


    }


    int hue = 0;
    int pos = 0;

    @Override
    public void periodic() {
        buffer.setHSV(pos, hue, 255, 200);
        led.setData(buffer);

        hue = (hue + 1) % 255;
        pos = (pos + 1) % ledCount;
    }
}
