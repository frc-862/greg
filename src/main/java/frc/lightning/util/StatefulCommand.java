package frc.lightning.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatefulCommand extends CommandBase {
    private Enum<?> state;
    protected Runnable default_action = () -> {};
    private Enum<?> previous_state = null;
    private Enum<?> calling_state = null;
    private double stateEnterTime;

    public void setState(Enum<?> new_state) {
        state = new_state;
    }

    public Enum<?> getState() {
        return state;
    }

    public Enum<?> getCallingState() {
        return calling_state;
    }

    protected void setDefaultAction(Runnable action) {
        default_action = action;
    }

    public StatefulCommand(Enum<?> state) {
        this.state = state;
    }

    @Override
    public void initialize() {
        previous_state = null;
        calling_state = state;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    private boolean call(String method_name) {
        try {
            Method method = getClass().getMethod(method_name);
            method.invoke(this);
        } catch (NoSuchMethodException | SecurityException |
                     IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException e) {
            System.err.println("StatefulCommand missing method: " + method_name);
            return false;
        }
        return true;
    }

    protected String methodName(Enum<?> state) {
        String state_name = state.name().toLowerCase();
        String method_name = Stream.of(state_name.split("[^a-zA-Z0-9]"))
                             .map(v -> v.substring(0, 1).toUpperCase() + v.substring(1).toLowerCase())
                             .collect(Collectors.joining());
        method_name = method_name.substring(0, 1).toLowerCase() + method_name.substring(1);
        return method_name;
    }

    @Override
    public void execute() {
        if (previous_state != state) {
            if (previous_state != null) {
                String exit_method = methodName(previous_state) + "Exit";
                call(exit_method);
            }

            previous_state = state;
            calling_state = state;
            stateEnterTime = Timer.getFPGATimestamp();
            call(methodName(state) + "Enter");
        }

        if (!call(methodName(state))) {
            this.default_action.run();
        }
    }

    public double timeInState() {
        return Timer.getFPGATimestamp() - stateEnterTime;
    }
}
