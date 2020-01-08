package frc.robot.commands;

import static org.junit.Assert.assertEquals;

import frc.lightning.subsystems.LightningDrivetrain;
import org.junit.Test;
import org.mockito.Mock;

public class ArcadeDriveTest {
    @Mock
    LightningDrivetrain drive;

    @Test
    public void movesForward() {
        ArcadeDrive arcade = new ArcadeDrive(drive, () -> 0.5, () -> 0);
//        int sum = calculator.evaluate("1+2+3");
//        assertEquals(6, sum);
    }
}
