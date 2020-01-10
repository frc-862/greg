package frc.robot.commands;

import static org.mockito.Mockito.*;

import frc.robot.subsystems.Collector;
import org.junit.Test;

public class CollectorTest {
    @Test
    public void stopped() {
        Collector collector = mock(Collector.class);
        Collect collect = new Collect(collector, () -> 0, () -> 0);
        collect.execute();

        verify(collector, never()).stop();
        verify(collector).setPower(0);
    }

    @Test
    public void collectIn() {
        Collector collector = mock(Collector.class);
        Collect collect = new Collect(collector, () -> 0, () -> 1);
        collect.execute();

        verify(collector, never()).stop();
        verify(collector).setPower(1);
    }

    @Test
    public void collectOut() {
        Collector collector = mock(Collector.class);
        Collect collect = new Collect(collector, () -> 1, () -> 0);
        collect.execute();

        verify(collector, never()).stop();
        verify(collector).setPower(-1);
    }

    @Test
    public void stopAtEnd() {
        Collector collector = mock(Collector.class);
        Collect collect = new Collect(collector, () -> 0, () -> 1);
        collect.end(false);

        verify(collector).stop();
    }
}
