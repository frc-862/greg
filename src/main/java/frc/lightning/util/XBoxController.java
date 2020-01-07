package frc.lightning.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class XBoxController extends Joystick {

    public XBoxController(final int port) {
        super(port);
    }

    public JoystickButton aButton = new JoystickButton(this, 1);
    public JoystickButton bButton = new JoystickButton(this, 2);
    public JoystickButton xButton = new JoystickButton(this, 3);
    public JoystickButton yButton = new JoystickButton(this, 4);
    public JoystickButton leftBumper = new JoystickButton(this, 5);
    public JoystickButton rightBumper = new JoystickButton(this, 6);
    public JoystickButton selectButton = new JoystickButton(this, 7);
    public JoystickButton startButton = new JoystickButton(this, 8);
    public JoystickButton leftStickButton = new JoystickButton(this, 9);
    public JoystickButton rightStickButton = new JoystickButton(this, 10);

    // public DPadUp dPadUp = new DPadUp(this);
    // public DPadDown dPadDown = new DPadDown(this);

    public double getLeftStickX() {
        return this.getRawAxis(0);
    }

    public double getLeftStickY() {
        return -this.getRawAxis(1);
    }

    public double getLeftTrigger() {
        return getRawAxis(2);
    }

    public double getRightTrigger() {
        return getRawAxis(3);
    }

    public double getThrottle() {
        return getRightTrigger() - getLeftTrigger();
    }

    public double getThrottleSquared() {
        final double left = getLeftTrigger();
        final double right = getRightTrigger();
        return (right * right) - (left * left);
    }

    public double getRightStickX() {
        return this.getRawAxis(4);
    }

    public double getRightStickY() {

        return -this.getRawAxis(5);
    }

    public int getDPad() {
        return getPOV();
    }

    public boolean isDPadUp() {
        int pos = getPOV();
        return pos != -1 && (pos > 270 || pos < 90);
    }

    public boolean isDPadRight() {
        int pos = getPOV();
        return pos > 0 && pos < 180;
    }

    public boolean isDPadDown() {
        int pos = getPOV();
        return pos > 90 && pos < 270;
    }

    public boolean isDPadLeft() {
        int pos = getPOV();
        return pos > 180 && pos < 360;
    }

    public void setLeftRumble(final double value) {
        setRumble(RumbleType.kLeftRumble, value);
    }

    public void setRightRumble(final double value) {
        setRumble(RumbleType.kRightRumble, value);
    }

    public void rumbleOff() {
        setLeftRumble(0);
        setRightRumble(0);
    }

}
