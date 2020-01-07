package frc.lightning.util;

/**
 * Interface for loops, which are routine that run periodically in the robot
 * code (such as periodic gyroscope calibration, etc.)
 */
public interface Loop {
    default void onStart() {}

    void onLoop();

    default void onStop() {}
}
