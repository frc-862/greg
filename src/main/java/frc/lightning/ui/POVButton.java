/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2016. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// verey similar to Joystick button class, but required values/methods were
// not exposed at a protected level -- only package

package frc.lightning.ui;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 *
 * @author bradmiller
 */
public class POVButton extends Button {

    final GenericHID joystick;
    final int position;

    /**
     * Create a joystick button for triggering commands
     *$
     * @param joystick The GenericHID object that has the button (e.g. Joystick,
     *        KinectStick, etc)
     * @param buttonNumber The button number (see
     *        {@link GenericHID#getRawButton(int) }
     */
    public POVButton(GenericHID joystick, int buttonNumber) {
        this.joystick = joystick;
        this.position = buttonNumber;
    }

    /**
     * Gets the value of the joystick button
     *$
     * @return The value of the joystick button
     */
    public boolean get() {
        return joystick.getPOV() == position;
    }
}
