package frc.robot.commands;

import static org.mockito.Mockito.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.robot.commands.drivetrain.ArcadeDrive;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ArcadeDriveTest {
    @Test
    public void stopped() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0, () -> 0);
        arcade.execute();

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(0,0));
    }

    @Test
    public void moveForward() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);

        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 1, () -> 0);
        arcade.execute();

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(1,1));
    }

    @Test
    public void autoQuickSpin() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0, () -> 1);
        arcade.execute();

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(1,-1));
    }

    @Test
    public void stopAtEnd() {
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0, () -> 1);
        arcade.end(false);

        verify(drive).stop();
    }
}
