package frc.robot.systemtests;

import frc.lightning.testing.SystemTest;
import frc.robot.JoystickConstants;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.lightning.fault.FaultCode;

public class ControllerTest extends SystemTest {
    private Joystick driverLeft = new Joystick(JoystickConstants.DRIVER_LEFT);
    private Joystick driverRight = new Joystick(JoystickConstants.DRIVER_RIGHT);
    private XboxController operator = new XboxController(JoystickConstants.OPERATOR);
    private Joystick climberController = new Joystick(JoystickConstants.CLIMBER);
    private ArrayList<String> names = new ArrayList<>();

    public ControllerTest(Joystick driverLeft, Joystick driverRight, XboxController operator, Joystick climberController) {
        super("Controller Test", FaultCode.Codes.GENERAL);
        this.driverLeft = driverLeft;
        this.driverRight = driverLeft;
        this.operator = operator;
        this.climberController = climberController;
    }
    
    @Override
    public boolean didPass() {
        try{
            names.add(driverLeft.getName());
            names.add(driverRight.getName());
            names.add(operator.getName());
            names.add(climberController.getName());
            return !names.contains("") && !names.contains(null);
        } catch(Exception e){
            return false;
        }
    }
}
