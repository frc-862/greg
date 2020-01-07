package frc.lightning.util;

public class ExponentialSmoothingFilter implements ValueFilter {
    private boolean first_time = true;
    private double previous;
    private double smoothing_factor;
    private double reverse_factor;

    public ExponentialSmoothingFilter(double smoothing_factor) {
        this.smoothing_factor = smoothing_factor;
        reverse_factor = 1 - smoothing_factor;
        reset();
    }

    @Override
    public void reset() {
        first_time = true;
        previous = 0;
    }

    public double filter(double value) {
        if (first_time) {
            first_time = false;
            previous = value;
        } else {
            previous = smoothing_factor * value + reverse_factor * previous;
        }
        return previous;
    }

    @Override
    public double get() {
        return previous;
    }
}
