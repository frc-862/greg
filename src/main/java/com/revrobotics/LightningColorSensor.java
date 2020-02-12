package com.revrobotics;

import edu.wpi.first.wpilibj.I2C;

public class LightningColorSensor extends ColorSensorV3 {
    /**
     * Constructs a ColorSensor.
     *
     * @param port The I2C port the color sensor is attached to
     */
    public LightningColorSensor(I2C.Port port) {
        super(port);
        configureColorSensor(ColorSensorResolution.kColorSensorRes17bit,ColorSensorMeasurementRate.kColorRate25ms,GainFactor.kGain9x);
    }
}
