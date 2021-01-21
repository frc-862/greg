package frc.lightning.commands;

import org.junit.Test;

import frc.lightning.subsystems.LightningDrivetrain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CurvatureDriveTest {
    @Test
    public void stopped() {
        final CurvatureDrive cDrive = new CurvatureDrive();
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        final var cmd = cDrive.curvatureDrive(0d, 0d, false);

        drive.setPower(cmd);

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(0,0));
    }

    @Test
    public void moveForward(){
        final CurvatureDrive cDrive = new CurvatureDrive();
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        final var cmd = cDrive.curvatureDrive(1d, 0d, false);

        drive.setPower(cmd);

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(1,1));
    }

    @Test
    public void quickSpinRight() {
        final CurvatureDrive cDrive = new CurvatureDrive();
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        final var cmd = cDrive.curvatureDrive(0d, 1d, true);

        drive.setPower(cmd);

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(1,-1));
    }

    @Test
    public void quickSpinLeft() {
        final CurvatureDrive cDrive = new CurvatureDrive();
        LightningDrivetrain drive = mock(LightningDrivetrain.class);
        final var cmd = cDrive.curvatureDrive(0d, -1d, true);

        drive.setPower(cmd);

        verify(drive, never()).stop();
        verify(drive).setPower(new LightningDrivetrain.DriveCommand(-1,1));
    }

}
