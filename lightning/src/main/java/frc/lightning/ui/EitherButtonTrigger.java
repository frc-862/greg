package frc.lightning.ui;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Created by phurley on 12/7/16.
 *
 * Only performs the action, when either button is pressed.
 * if you want to bind the same command to the same button
 * on two controllers.
 *
 */
public class EitherButtonTrigger extends Trigger {
    final JoystickButton button1;
    final JoystickButton button2;

    public EitherButtonTrigger(JoystickButton b1, JoystickButton b2) {
        button1 = b1;
        button2 = b2;
    }

    @Override
    public boolean get() {
        return button1.get() || button2.get();
    }
}


