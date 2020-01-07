package frc.lightning.testing;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.lightning.util.FaultCode;

abstract public class SystemTest implements Comparable<SystemTest> {

    public static void register(SystemTest test) {
        SystemTestCommand.register(test);
    }

    enum Priority {
        HIGH, MED, LOW, DONT_CARE
    }

    private FaultCode.Codes code;
    private Priority priority;

    public SystemTest(FaultCode.Codes code) {
        this(code, Priority.DONT_CARE);
    }

    public SystemTest(FaultCode.Codes code, Priority priority) {
        this.code = code;
        this.priority = priority;
    }

    private double startedAt;

    public void starting() {
        startedAt = Timer.getFPGATimestamp();
    }

    public double timeSinceInitialized() {
        return Timer.getFPGATimestamp() - startedAt;
    }

    public Priority getPriority() {
        return priority;
    }

    public Subsystem requires() {
        return null;
    }

    public Subsystem[] requiresMultiple() {
        return new Subsystem[] { requires() };
    }

    public void setup() {/* Config - Talon Modes */}
    public void tearDown() {/* Set Powers to 0.0d */}
    abstract public boolean didPass();
    public boolean isFinished() {
        return true;
    }
    public void periodic() {/* Actuation Here */}

    public FaultCode.Codes getCode() {
        return code;
    }

    @Override
    public int compareTo(SystemTest other) {
        return priority.compareTo(other.priority);
    }
}