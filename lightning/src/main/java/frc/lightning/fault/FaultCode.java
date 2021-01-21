package frc.lightning.fault;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Error codes for robot and means to log them
 */
public class FaultCode {

    /**
     * All possible fault codes on the robot
     */
    public enum Codes {
        LEFT_ENCODER_NOT_FOUND,
        RIGHT_ENCODER_NOT_FOUND,
        LOW_MAIN_VOLTAGE,
        SLOW_LOOPER,
        MISMATCHED_MOTION_PROFILES,
        NAVX_ERROR,
        INTERNAL_ERROR,
        DRIVETRAIN,
        LEFT_DRIVE_FAILURE,
        RIGHT_DRIVE_FAILURE,
        DRIVE_MASTER_ERROR,
        DRIVE_PRIMARY_SLAVE_ERROR,
        DRIVE_SECONDARY_SLAVE_ERROR,
        RIGHT_MOTORS_OUT_OF_SYNC,
        LEFT_MOTORS_OUT_OF_SYNC,
        GENERAL;
    }

    private static HashSet<Codes> faults = new HashSet<>();
    private static Map<Codes, NetworkTableEntry> networkTableMap = new HashMap<>();
    private static boolean dummy_light = false;

    /**
     * Links a {@link frc.lightning.fault.FaultCode.Codes} with a {@link edu.wpi.first.networktables.NetworkTableEntry}
     * @param code The {@link frc.lightning.fault.FaultCode.Codes} that needs to be linked with an NT entry
     * @param nte The {@link edu.wpi.first.networktables.NetworkTableEntry} to link the fault to
     */
    public static void setNetworkTableEntry(Codes code, NetworkTableEntry nte) {
        networkTableMap.put(code, nte);
    }

    static {
        eachCode((Codes c, Boolean state) -> {
            SmartDashboard.putBoolean("FAULT_" + c.toString(), state);
        });
        try {
            Files.write(getFaultPath(), ("######### RESTART #########\n").getBytes(), StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Unable to append to fault log file: " + getFaultPath() + ": " + e);
            e.printStackTrace();
        }
    }

    /**
     * Fault path log file
     * @return The path to the fault log file
     */
    private static Path getFaultPath() {
        return Paths.get("/home/lvuser/faults.log");
    }

    /**
     * Writes the given fault code
     * @param code The {@link frc.lightning.fault.FaultCode.Codes} to be written
     */
    public static void write(Codes code) {
        write(code, "");
    }

    /**
     * Updates the {@link edu.wpi.first.networktables.NetworkTableEntry} for each 
     * {@link frc.lightning.fault.FaultCode.Codes}
     */
    public static void update() {
        eachCode((Codes c, Boolean state) -> {
            final var entry = networkTableMap.get(c);
            if (entry != null) entry.setBoolean(state);
        });
    }

    /**
     * Runs the given function on each code. Something like:
     * 
     * eachCode((Codes c, Boolean state) -> {
     *     // Do Something
     * });
     * 
     * @param fn The {@link java.util.function.BiConsumer} function to perform on each code. 
     * The function should take a code and state (true or false) as parameters.
     */
    public static void eachCode(BiConsumer<Codes, Boolean> fn) {
        for (Codes c : Codes.values()) {
            fn.accept(c, !faults.contains(c));
        }
    }

    /**
     * Writes the fault code, effectively logging that it has been detected in 
     * a log file, the system error stream, and on the {@link edu.wpi.first.wpilibj.smartdashboard.SmartDashboard}
     * @param code The {@link frc.lightning.fault.FaultCode.Codes} to be written
     * @param msg The message to write to the log file
     */
    public static void write(Codes code, String msg) {
        dummy_light = true;
        try {
            if (!faults.contains(code)) {
                faults.add(code);
                SmartDashboard.putBoolean("FAULT_" + code.toString(), false);

                Files.write(Paths.get("/home/lvuser/faults.log"),
                            ("FAULT Detected: " + code.toString() + " " + msg + "\n").getBytes(),
                            StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND);
                System.err.println("FAULT: " + code + " " + msg);
            }
        } catch (IOException e) {
            System.err.println("Unable to write fault code " + code);
            e.printStackTrace();
        }
    }
    public boolean dummyLightOn() {
        return dummy_light;
    }

    /**
     * Gets all the faults as a JSON String
     * @return The faults in JSON format
     */
    public static String toJSONString() {
        return faults.stream().map((c) -> "\"" + c.toString() + "\"").collect(Collectors.joining(",","[","]"));
    }

    /**
     * Gets a model of the faults and their states as well as the timestamp
     * @return The model of faults
     */
    public static Map<String, Object> getModel() {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> faults = new HashMap<>();
        eachCode((Codes c, Boolean state) -> {
            faults.put("FAULT_" + c.toString(), state);
        });
        result.put("faults", faults);
        result.put("timer", Timer.getFPGATimestamp());

        return result;
    }

}
