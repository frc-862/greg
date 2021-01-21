package frc.lightning.auto;

import org.junit.Test;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class AutonomousTest {

    @Test
    public void dashboardWait() {
        assertTrue(Autonomous.hasDashboardWaitCommand());
        Autonomous.setHasDashboardWaitCommand(false);
        assertTrue(!Autonomous.hasDashboardWaitCommand());
    }

    @Test
    public void emptyAuto() {
        Autonomous.load();
        assertNull(Autonomous.getAutonomous());
    }

    @Test
    public void singleAuto() {
        Autonomous.register("TEST", new InstantCommand());
        assertEquals((double) Autonomous.getCommands().size(), 1d, Math.ulp(1d));
        assertTrue(Autonomous.getCommands().keySet().contains("TEST"));
    }
    
}
