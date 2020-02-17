package frc.lightning.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FaultCode {
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
        DRIVE_SECONDARY_SLAVE_ERROR;
    }

    private static HashSet<Codes> faults = new HashSet<>();
    private static Map<Codes, NetworkTableEntry> networkTableMap = new HashMap<>();
    private static boolean dummy_light = false;

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

    private static Path getFaultPath() {
        return Paths.get("/home/lvuser/faults.log");
    }

    public static void write(Codes code) {
        write(code, "");
    }

    public static void update() {
        eachCode((Codes c, Boolean state) -> {
            final var entry = networkTableMap.get(c);
            if (entry != null) entry.setBoolean(state);
        });
    }

    public static void eachCode(BiConsumer<Codes, Boolean> fn) {
        for (Codes c : Codes.values()) {
            fn.accept(c, !faults.contains(c));
        }
    }

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

    //@SuppressWarnings("unchecked")
    public static String toJSONString() {
        // JSONObject json = new JSONObject();
        // eachCode((Codes c, Boolean state) -> {
        //     json.put("FAULT_" + c.toString(), state);
        // });
        // return json.toJSONString();
        return faults.stream().map((c) -> "\"" + c.toString() + "\"").collect(Collectors.joining(",","[","]"));
    }

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
