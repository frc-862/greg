package frc.lightning.logging;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.lightning.util.Loop;

public class DataLogger implements Loop {
    private static final int max_lines = 15000;
    private static DataLogger logger;
    private static String baseFName = "greg";

    private LogWriter writer;
    private ArrayList<String> fieldNames = new ArrayList<>();
    private ArrayList<DoubleSupplier> fieldValues = new ArrayList<>();
    private boolean first_time = true;
    private boolean preventNewElements = false;

    private int line_count = 0;

    public static DataLogger getLogger() {
        if (logger == null) {
            logger = new DataLogger();
            logger.addElement("Timestamp", () -> Timer.getFPGATimestamp());
        }
        return logger;
    }

    public LogWriter getLogWriter() {
        return writer;
    }

    public static void addDataElement(String name, DoubleSupplier val) {
        DataLogger.getLogger().addElement(name, val);
    }

    public static void addDelayedDataElement(String name, DoubleSupplier val) {
        DataLogger.getLogger().addElement(name, new DataLoggerOutOfBand(val));
    }

    public void addElement(String name, DoubleSupplier val) {
        if (preventNewElements) {
            System.err.println("Unexpected call to addDataElement: " + name);
        } else {
            fieldNames.add(name);
            fieldValues.add(val);
        }
    }

    public void onStart() {
        if (first_time) {
            writeHeader();
            first_time = false;
            writer.flush();
        }
    }

    public void onStop() {
        writer.flush();
    }

    public String getHeader() {
        return fieldNames.stream().collect(Collectors.joining(","));
    }

    private void writeHeader() {
        writer.logRawString(getHeader());
    }

    private void writeValues() {
        line_count += 1;
        if (line_count >= max_lines) {
            reset_file();
        }

        String valueList = fieldValues.parallelStream()
                           .map(fn -> Double.toString(fn.getAsDouble()))
//        .map(fn -> "0.0")
                           .collect(Collectors.joining(","));

        // System.out.println(valueList);
        writer.logRawString(valueList);
    }

    private DataLogger() {
        File file = logFileName();
        writer = new LogWriter(file.getAbsolutePath());
    }

    private static boolean foundDS = false;
    public static void checkBaseFileName() {
        if (foundDS) return;

        var ds = DriverStation.getInstance();
        if (ds != null) {
            var matchType = ds.getMatchType();
            if (matchType != DriverStation.MatchType.None) {
                String newName = String.format("%s-%s-%d",
                                               ds.getEventName(), matchType.toString(), ds.getMatchNumber()
                                              );

                if (!Objects.equals(newName, baseFName)) {
                    setBaseFileName(newName);
                    foundDS = true;
                }
            }
        }
    }

    public static void setBaseFileName(String fname) {
        baseFName = fname;
        getLogger().reset_file();
    }

    private File cachedLogFileName = null;
    private File logFileName() {
        if (cachedLogFileName != null)
            return cachedLogFileName;

        File base = null;

        // find the mount point
        char mount = 'u';
        while (base == null && mount <= 'z') {
            File f = new File("/" + mount);
            if (f.isDirectory()) {
                base = f;
            }
            ++mount;
        }

        if (base == null) {
            base = new File("/home/lvuser");
        }

        base = new File(base, "log");
        System.out.println("Log to " + base);

        //noinspection ResultOfMethodCallIgnored
        base.mkdirs();

        String name_format = baseFName + "-%05d-dl.log";
        int counter = 1;
        File result = new File(base, String.format(name_format, counter));
        while (result.exists()) {
            result = new File(base, String.format(name_format, ++counter));
        }
        System.out.println("Logging to " + result);

        cachedLogFileName = result;
        return result;
    }

    public void reset_file() {
        cachedLogFileName = null;
        flush();
        writer.setFileName(logFileName().getAbsolutePath());
        writeHeader();
        line_count = 0;
    }

    public static void flush() {
        System.out.println("Datalogger flush");
        getLogger().writer.drain();
        getLogger().writer.flush();
    }

    @Override
    public void onLoop() {
        writeValues();
    }

    public static void logData() {
        getLogger().writeValues();
    }

    public static void preventNewDataElements() {
        getLogger().lockItUp();
    }

    private void lockItUp() {
        logger.preventNewElements = true;
        onStart();
    }
}
