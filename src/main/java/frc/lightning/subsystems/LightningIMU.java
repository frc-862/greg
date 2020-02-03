package frc.lightning.subsystems;

public interface LightningIMU {
    public default double getHeading() { return 0; }
    public default void resetHeading() {}
}
