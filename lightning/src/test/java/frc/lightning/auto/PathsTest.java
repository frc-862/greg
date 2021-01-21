package frc.lightning.auto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathsTest {

    @Test
    public void empty() {
        assertEquals((double) frc.lightning.auto.Paths.getPaths().size(), 0d, Math.ulp(1d));
    }

    @Test
    public void addRemove() {
        frc.lightning.auto.Paths.register("TEST", new frc.lightning.auto.Path("Name", null));
        assertEquals(frc.lightning.auto.Paths.getPath("TEST").getName(), "Name");
        assertEquals((double) frc.lightning.auto.Paths.getPaths().size(), 1d, Math.ulp(1d));
        frc.lightning.auto.Paths.removePath("TEST");
        assertEquals((double) frc.lightning.auto.Paths.getPaths().size(), 0d, Math.ulp(1d));
    }
    
}
