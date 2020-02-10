package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CtrlPanelOperator;

public class PositionWheel extends CommandBase {
    CtrlPanelOperator ctrlPanelOperator;
    double pwr = .25;
    int setpoint;
    int passed = -1;
    double timesetpoint;
    double trust = 0;
    boolean first = true;
    boolean done = false;
    boolean Worthy;
    boolean close;
    int scale;
    int targetColor;

    int currentColor;

    public PositionWheel(CtrlPanelOperator ctrlPanelOperator) {
        timesetpoint = Timer.getFPGATimestamp();
        this.ctrlPanelOperator = ctrlPanelOperator;

        addRequirements(ctrlPanelOperator);


    }

    @Override
    public void initialize() {
        pwr = .25;
        trust = 0;
        passed = -1;
        done = false;
        Worthy = false;
        close=false;
        setpoint = ctrlPanelOperator.currentColor();
        currentColor = ctrlPanelOperator.currentColor();
        scale = 5;
        targetColor = ctrlPanelOperator.getFMSMsg();
    }

    @Override
    public void execute() {


        if (targetColor == -1){
            done = true;
        }

            if (currentColor == ctrlPanelOperator.currentColor()) {
            trust += 1;
        } else {
            trust = 0;
        }

        currentColor = ctrlPanelOperator.currentColor();

        if (trust == scale) {
            Worthy = true;
        } else {
            Worthy = false;
        }


        if (targetColor == ctrlPanelOperator.currentColor() && Worthy) {
            timesetpoint = Timer.getFPGATimestamp();
            close=true;
        }
        if (close){
            if((Timer.getFPGATimestamp() - timesetpoint <= .14)){
                pwr = -.7;
            } else {
                pwr=0;
                done=true;
            }
        }


//        if (passed >= 6) {
//            pwr = .175;
//        }

//        if (passed == 7) {
//            timesetpoint = Timer.getFPGATimestamp();
//            done = true;
//        }
//        if(passed >= 9){
//
//            if(!(Timer.getFPGATimestamp() - timesetpoint >= .2)){
//            pwr =- .175;
//            } else {
//                pwr=0;
//                done=true;
//            }
//        }

        ctrlPanelOperator.setPower(pwr);
        System.out.println("CW: " + trust + " trust " + pwr + " Pwr \n" + currentColor + " current color " + targetColor + " targetColor");
    }

    @Override
    public boolean isFinished() {
        return done;
    }

    @Override
    public void end(boolean i) {
        pwr = 0;
    }
}
