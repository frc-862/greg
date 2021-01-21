package frc.lightning.ui;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 *
 * Only performs the action, when both buttons are pressed. Can be used
 * to guard against dangerous actions or to setup a set of "shift" operations
 * if you run out of buttons.
 *
 */
public class TwoButtonTrigger extends Trigger {
    JoystickButton button1;
    JoystickButton button2;

    public TwoButtonTrigger(JoystickButton b1, JoystickButton b2) {
        button1 = b1;
        button2 = b2;
    }

    @Override
    public boolean get() {
        return button1.get() && button2.get();
    }
}


