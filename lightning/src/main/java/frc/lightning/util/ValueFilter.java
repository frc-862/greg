package frc.lightning.util;

/**
 * Created by phurley on 12/7/16.
 */
public interface ValueFilter {
    void reset();
    double filter(double value);
    double get();
}
