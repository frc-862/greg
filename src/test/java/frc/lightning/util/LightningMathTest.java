package frc.lightning.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LightningMathTest {
    
    @Test
    public void limitBoundsTest() {
        double testValue = 5d;
        assertEquals(LightningMath.limit(testValue, 10d, 15d),  10d,  Math.ulp(1d));
        assertEquals(LightningMath.limit(testValue, 0d, 10d),   5d,   Math.ulp(1d));
        assertEquals(LightningMath.limit(testValue, -5d, 0d),   0d,   Math.ulp(1d)); 
        assertEquals(LightningMath.limit(0d, 0d, 10d),          0d,   Math.ulp(1d)); 
        assertEquals(LightningMath.limit(10, 0d, 10d),          10d,  Math.ulp(1d));        
    }

}
