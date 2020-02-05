package frc.lightning.util;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class InterpolatedMapTest {
    @Test
    public void find_same_value() {
        InterpolatedMap table = new InterpolatedMap();
        table.put(1.0, 2.0);
        table.put(10.0, 20.0);

        assertEquals(2.0,table.get(1.0),0.0000000001);
        assertEquals(20.0,table.get(10.0),0.0000000001);
    }

    @Test
    public void test_interpolation() {
        InterpolatedMap table = new InterpolatedMap();
        table.put(1.0, 2.0);
        table.put(10.0, 20.0);

        assertEquals(10.0,table.get(5.0),0.0000000001);
        assertEquals(14.0,table.get(7.0),0.0000000001);
    }

    @Test
    public void test_table_construction() {
        InterpolatedMap table = new InterpolatedMap(1d, 2d, 2d, 4d, 3d, 9d, 4d, 16d, 10d, 100d);

        assertEquals(2.0,table.get(1.0),0.0000000001);
        assertEquals(9.0,table.get(3.0),0.0000000001);
        assertEquals(58.0,table.get(7.0),0.0000000001);
        assertEquals(100.0,table.get(10.0),0.0000000001);
    }
}
