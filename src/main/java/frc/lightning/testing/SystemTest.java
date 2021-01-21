package frc.lightning.testing;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.fault.FaultCode;

public abstract class SystemTest extends CommandBase implements Comparable<SystemTest> {

    private String msg;

    public static void register(SystemTest test) {
        SystemTestCommand.register(test);
    }

    public enum Priority {
        DO_FIRST, HIGH, MED, LOW, DONT_CARE
    }

    private FaultCode.Codes code;
    private Priority priority;

    public SystemTest(String msg, FaultCode.Codes code) {
        this(msg, code, Priority.DONT_CARE);
    }

    public SystemTest(String msg, FaultCode.Codes code, Priority priority) {
        this.msg = msg;
        this.code = code;
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getMessage() {
        return msg;
    }

    abstract public boolean didPass();

    public FaultCode.Codes getCode() {
        return code;
    }

    @Override
    public int compareTo(SystemTest other) {
        return priority.compareTo(other.priority);
    }

}