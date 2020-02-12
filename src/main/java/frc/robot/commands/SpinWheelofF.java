package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CtrlPanelOperator;

public class SpinWheelofF extends CommandBase {
    CtrlPanelOperator ctrlPanelOperator;
    double pwr = .25;
    int setpoint;
    int passed = -1;
    double timesetpoint;
    double trust = 0;
    boolean first = true;
    boolean done = false;
    boolean Worthy;
    int scale;

    int currentColor;

    public SpinWheelofF(CtrlPanelOperator ctrlPanelOperator) {
        timesetpoint = Timer.getFPGATimestamp();
        this.ctrlPanelOperator = ctrlPanelOperator;

        addRequirements(ctrlPanelOperator);


    }

    @Override
    public void initialize() {
        pwr = .4;
        trust = 0;
        passed = -1;
        done = false;
        Worthy = false;
        setpoint = ctrlPanelOperator.currentColor();
        currentColor = ctrlPanelOperator.currentColor();
        scale = 8;
    }

    @Override
    public void execute() {
        // if the color from the past cycle is equal to the current color then the trust increases by 1??????
        if (currentColor == ctrlPanelOperator.currentColor()) {
            trust += 1;
        } else {
            trust = 0;
        }
// this updates the current color?????
        currentColor = ctrlPanelOperator.currentColor();
// if color has been the same for scale cycles then it is trustworthy
        if (trust == scale) {
            Worthy = true;
        } else {
            Worthy = false;
        }
// if the current color is the set point then it has passed the current color one more time
        if (setpoint == ctrlPanelOperator.currentColor() && Worthy) {
            passed++;
        }

// if it has passed the color more than 6 times it will slow down
        if (passed >= 6) {
            pwr = .175;
        }
// if it passes the color 7 times it will stop
        if (passed == 7) {
            timesetpoint = Timer.getFPGATimestamp();
            done = true;
        }
//        if(passed>=9){
//
//            if(!(Timer.getFPGATimestamp() - timesetpoint >= .2)){
//            pwr=-.175;
//            }else {
//                pwr=0;
//                done=true;
//            }
//        }
// prints stuff so you can see it
        ctrlPanelOperator.setPower(pwr);
        System.out.println("CW: " + passed + " passed " + trust + " trust " + pwr + " Pwr \n" + currentColor + " current color " + setpoint + " setpoint");
    }

    // stuff is done
    @Override
    public boolean isFinished() {
        return done;
    }

    @Override
    public void end(boolean i) {
        pwr = 0;
    }
}
