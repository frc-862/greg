package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CtrlPanelOperator;

import java.util.function.DoubleSupplier;

public class Manual extends CommandBase {
    CtrlPanelOperator ctrlPanelOperator;
    DoubleSupplier pwr;
    public Manual(CtrlPanelOperator ctrlPanelOperator, DoubleSupplier pwr) {
        this.ctrlPanelOperator =ctrlPanelOperator;
        this.pwr =pwr;
        addRequirements(ctrlPanelOperator);
    }
    @Override
    public void execute() {

        ctrlPanelOperator.setPower(pwr.getAsDouble());

    }
    @Override
    public boolean isFinished() {
        return  false;
    }
}
