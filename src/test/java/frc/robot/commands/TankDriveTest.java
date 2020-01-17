package frc.robot.commands;

import static org.mockito.Mockito.*;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.commands.drivetrain.TankDrive;
import org.junit.Test;

public class TankDriveTest {
    @Test
    public void stopped() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        TankDrive tank = new TankDrive(drive, () -> 0, () -> 0);
        tank.execute();

        verify(drive, never()).stop();
        verify(drive).setPower(0,0);
    }

    @Test
    public void moveForward() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);

        TankDrive tank = new TankDrive(drive, () -> 1, () -> 1);
        tank.execute();

        verify(drive, never()).stop();
        verify(drive).setPower(1,1);
    }

    @Test
    public void autoQuickSpin() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        TankDrive tank = new TankDrive(drive, () -> 1, () -> -1);
        tank.execute();

        verify(drive, never()).stop();
        verify(drive).setPower(1,-1);
    }

    @Test
    public void stopAtEnd() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        TankDrive tank = new TankDrive(drive, () -> 0, () -> 1);
        tank.end(false);

        verify(drive).stop();
    }
}
