package frc.lightning.util;

public class KalmanFilter implements ValueFilter {
    private double kQ;
    private double kR;
    private double prevP;
    private double prevEstimate;
    private boolean first_time;

    public KalmanFilter(double kQ, double kR) {
        this.kQ = kQ;
        this.kR = kR;
        reset();
    }

    public KalmanFilter() {
        this(0.024, 0.6158);
    }

    @Override
    public void reset() {
        prevP = 0.0;
        prevEstimate = 0.0;
        first_time = true;
    }

    @Override
    public double filter(double value) {
        if (first_time) {
            prevEstimate = value;
            first_time = false;
        }

        double tempP = prevP + kQ;
        double k = tempP / (tempP + kR);
        double xEst = prevEstimate + k * (value - prevEstimate);

        prevP = (1 - k) * tempP;
        prevEstimate = xEst;

        return prevEstimate;
    }

    @Override
    public double get() {
        return prevEstimate;
    }

}
