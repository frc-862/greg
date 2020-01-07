package frc.lightning.util;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class FaultCommand extends InstantCommand {
    final FaultCode.Codes code;

    public FaultCommand(FaultCode.Codes code) {
        this.code = code;
    }

    @Override
    public void initialize() {
        System.out.println("FaultCommand initialize: " + code);
        FaultCode.write(code);
    }
}
