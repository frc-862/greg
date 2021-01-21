package frc.lightning.util;

public class MovingAverageFilter implements ValueFilter {
    private final double[] values;
    private double average = 0;
    private int pos = 0;
    private int count = 0;

    public MovingAverageFilter(int boxes) {
        values = new double[boxes];
    }

    @Override
    public void reset() {
        average = 0;
        pos = 0;
        count = 0;
    }

    @Override
    public double filter(double value) {
        if (count < values.length) {
            average = (average * count + value) / (count + 1);
            count += 1;
        } else {
            average += (value - values[pos]) / values.length;
        }

        values[pos] = value;
        pos = (pos + 1) % values.length;

        return average;
    }

    @Override
    public double get() {
        return average;
    }
}
