package frc.lightning.util;

import frc.lightning.util.FaultCode.Codes;

public abstract class AbstractFaultMonitor {
    protected Codes code;
    protected String msg;

    public AbstractFaultMonitor(Codes code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AbstractFaultMonitor(Codes code) {
        this(code, "FAULT " + code.name());
    }

    public abstract boolean checkFault();

    public boolean check() {
        if (checkFault()) {
            trigger();
            return true;
        }
        return false;
    }

    public void trigger() {
        FaultCode.write(code, msg);
    }
}