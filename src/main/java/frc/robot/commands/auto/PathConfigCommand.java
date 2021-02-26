package frc.robot.commands.auto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.JoystickFilter;

/**
 * TeleOp mode that write waypoints to a file to be used in a path. 
 * File format: X Y Theta\n
 */
public class PathConfigCommand extends CommandBase {

	private final double DEADBAND = 0.1;
    private final double MIN_PWR = 0.1;
    private final double MAX_PWR = 0.2;

	private final LightningDrivetrain drivetrain;

	private final DoubleSupplier leftThrottle;
    private final DoubleSupplier rightThrottle;
    
    private final JoystickFilter filter;

	private ShuffleboardTab tab;

	private NetworkTableEntry pathNameEntry;
	
	private FileWriter writer;

	public PathConfigCommand(LightningDrivetrain drivetrain, DoubleSupplier leftThrottle, DoubleSupplier rightThrottle) {
		this.drivetrain = drivetrain;
		this.leftThrottle = leftThrottle;
		this.rightThrottle = rightThrottle;

		tab = Shuffleboard.getTab("Path Config");
		pathNameEntry = tab.add("Path Name", "path").getEntry();

		filter = new JoystickFilter(DEADBAND, MIN_PWR, MAX_PWR, JoystickFilter.Mode.CUBED);

		addRequirements(drivetrain);
	}

	private File getFile() {
		File base = null;
		char mount = 'u';
		while (base == null && mount <= 'z') {
			File f = new File("/" + mount);
			if (f.isDirectory())
				base = f;
			++mount;
		}
		if (base == null) base = new File("/home/lvuser");
		base = new File(base, "paths");
		System.out.println("Write paths to " + base);
		base.mkdirs();
		String name = pathNameEntry.getString("path") + ".waypoints";
		String destFile = base.getAbsolutePath() + name;
		return new File(destFile);
	}

	private FileWriter getWriter() throws IOException {
		return new FileWriter(getFile());
	}

	private void write(String str) throws IOException {
		if(writer != null) writer.write(str + "\n");
	}

	@Override
	public void initialize() {
		try{
			writer = getWriter();
			write("X Y Theta");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void execute() {

		// Write Pose
		try {
			double x = drivetrain.getPose().getX();
			double y = drivetrain.getPose().getY();
			double theta = drivetrain.getPose().getRotation().getDegrees();
			write(x + " " + y + " " + theta);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// Drive
		double left = filter.filter(leftThrottle.getAsDouble());
		double right = filter.filter(rightThrottle.getAsDouble());
		drivetrain.setPower(left, right);

	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		drivetrain.stop();
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
