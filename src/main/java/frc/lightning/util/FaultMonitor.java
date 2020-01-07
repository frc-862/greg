package frc.lightning.util;

import java.util.LinkedList;
import java.util.function.BooleanSupplier;
import java.util.List;

import frc.lightning.util.FaultCode.Codes;

public class FaultMonitor extends AbstractFaultMonitor {
    private static List<AbstractFaultMonitor> monitors = new LinkedList<>();

    final BooleanSupplier fn;

    static public void register(AbstractFaultMonitor fm) {
        monitors.add(fm);
    }

    static public void checkMonitors() {
        monitors.removeIf((fm) -> fm.check());
    }

    public FaultMonitor(Codes code, BooleanSupplier fn, String msg) {
        super(code, msg);
        this.fn = fn;
    }

    public FaultMonitor(Codes code, BooleanSupplier fn) {
        this(code, fn, "FAULT: " + code.toString());
    }

    @Override
    public boolean checkFault() {
        return fn.getAsBoolean();
    }
}