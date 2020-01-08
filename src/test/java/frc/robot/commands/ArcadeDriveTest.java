package frc.robot.commands;

import static org.mockito.Mockito.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.lightning.subsystems.LightningDrivetrain;
import org.junit.Test;

public class ArcadeDriveTest {
    @Test
    public void stopped() {
        DifferentialDrive ddrive = mock(DifferentialDrive.class);
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        when(drive.getDifferentialDrive()).thenReturn(ddrive);

        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0, () -> 0);
        arcade.execute();

        verify(ddrive).curvatureDrive(anyDouble(), anyDouble(), anyBoolean());
        verify(ddrive).curvatureDrive(0, 0, false);
    }

    @Test
    public void moveForward() {
        DifferentialDrive ddrive = mock(DifferentialDrive.class);
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        when(drive.getDifferentialDrive()).thenReturn(ddrive);

        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 1, () -> 0);
        arcade.execute();

        verify(ddrive).curvatureDrive(anyDouble(), anyDouble(), anyBoolean());
        verify(ddrive).curvatureDrive(1, 0, false);
    }

    @Test
    public void autoQuickSpin() {
        DifferentialDrive ddrive = mock(DifferentialDrive.class);
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        when(drive.getDifferentialDrive()).thenReturn(ddrive);

        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0, () -> 1);
        arcade.execute();

        verify(drive, never()).stop();
        verify(ddrive).curvatureDrive(anyDouble(), anyDouble(), anyBoolean());
        verify(ddrive).curvatureDrive(0, 1, true);
    }

    @Test
    public void stopAtEnd() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);

        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0, () -> 1);
        arcade.end(false);

        verify(drive).stop();
    }
}
