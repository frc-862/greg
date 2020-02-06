package frc.lightning.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class VersionTest {
    @Test
    public void loadVersion() {
        Properties props = new Properties();
        try
        {
            props.load(ClassLoader.getSystemResourceAsStream("version.properties"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Assert.assertEquals("2020.1.0", props.getProperty("VERSION_NAME"));
    }
}
