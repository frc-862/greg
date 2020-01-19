package frc.robot.commands;

import static org.mockito.Mockito.*;

import frc.robot.commands.shooter.SpinUpFlywheelPower;
import frc.robot.subsystems.Shooter;
import org.junit.Test;

public class ShooterTest {
    @Test
    public void stopped() {
        Shooter shooter = mock(Shooter.class);
        SpinUpFlywheelPower shoot = new SpinUpFlywheelPower(shooter, () -> 0);
        shoot.execute();

        verify(shooter, never()).stop();
        verify(shooter).setPower(0);
    }

    @Test
    public void shooterOut() {
        Shooter shooter = mock(Shooter.class);
        SpinUpFlywheelPower shoot = new SpinUpFlywheelPower(shooter, () -> 1);
        shoot.execute();

        verify(shooter, never()).stop();
        verify(shooter).setPower(1);
    }

    @Test
    public void shooterIn() {
        Shooter shooter = mock(Shooter.class);
        SpinUpFlywheelPower shoot = new SpinUpFlywheelPower(shooter, () -> -1);
        shoot.execute();

        verify(shooter, never()).stop();
        verify(shooter).setPower(-1);
    }

    @Test
    public void stopAtEnd() {
        Shooter shooter = mock(Shooter.class);
        SpinUpFlywheelPower shoot = new SpinUpFlywheelPower(shooter, () -> 1);
        shoot.end(false);

        verify(shooter).stop();
    }
}
