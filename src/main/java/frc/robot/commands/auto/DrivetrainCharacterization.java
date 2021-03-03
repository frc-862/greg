package frc.robot.commands.auto;

import java.util.ArrayList;
import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.subsystems.LightningDrivetrain;

/**
 * Command to be run with frc-characterization tool.
 */
public class DrivetrainCharacterization extends CommandBase {

	private final LightningDrivetrain drivetrain;

	private Supplier<Double> leftEncoderPosition;
	private Supplier<Double> leftEncoderRate;
	private Supplier<Double> rightEncoderPosition;
	private Supplier<Double> rightEncoderRate;
	private Supplier<Double> gyroAngleRadians;

	private NetworkTableEntry autoSpeedEntry = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
	private NetworkTableEntry telemetryEntry = NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
	private NetworkTableEntry rotateEntry = NetworkTableInstance.getDefault().getEntry("/robot/rotate");

	private String data = "";

	private int counter;
	private double startTime;
	private double priorAutospeed = 0;

	private double[] numberArray = new double[10];
	private ArrayList<Double> entries = new ArrayList<Double>();

	public DrivetrainCharacterization(LightningDrivetrain drivetrain) {
		
		this.drivetrain = drivetrain;

		leftEncoderPosition = drivetrain::getLeftDistance;
		leftEncoderRate = drivetrain::getLeftVelocity;
		rightEncoderPosition = drivetrain::getRightDistance;
		rightEncoderRate = drivetrain::getRightVelocity;
		gyroAngleRadians = () -> drivetrain.getPose().getRotation().getRadians();
		
		addRequirements(drivetrain);

	}

	@Override
	public void initialize() {
		startTime = Timer.getFPGATimestamp();
		counter = 0;
	}

	@Override
	public void execute() {
		
		// Read Info For Analysis
		double timestamp = Timer.getFPGATimestamp(); // Get Timestamp
		double leftPosition = leftEncoderPosition.get(); // Read Left Encoder Position
		double leftRate = leftEncoderRate.get(); // Read Left Encoder Rate
		double rightPosition = rightEncoderPosition.get(); // Read Right Encoder Position
		double rightRate = rightEncoderRate.get(); // Read Right Encoder Rate
		double robotVoltage = RobotController.getBatteryVoltage(); // Read Real Battery Voltage
		double motorVolts = robotVoltage * Math.abs(priorAutospeed); // Read Voltage Output To Motors
		double leftMotorVolts = motorVolts; // Left Motor Output Volts
		double rightMotorVolts = motorVolts; // Right Motor Output Volts
		double autospeed = autoSpeedEntry.getDouble(0); // Read Speed Command from NT
		priorAutospeed = autospeed; // Set Prior Speed

		// Set Drivetrain Output
		drivetrain.setPower((rotateEntry.getBoolean(false) ? -1 : 1) * autospeed, autospeed);

		numberArray[0] = timestamp;
		numberArray[1] = robotVoltage;
		numberArray[2] = autospeed;
		numberArray[3] = leftMotorVolts;
		numberArray[4] = rightMotorVolts;
		numberArray[5] = leftPosition;
		numberArray[6] = rightPosition;
		numberArray[7] = leftRate;
		numberArray[8] = rightRate;
		numberArray[9] = gyroAngleRadians.get();

		// Add Data To NT Entry
		for (double num : numberArray) entries.add(num);
		
		// Increment Counter
		counter++;
	
	}

	@Override
	public void end(boolean interrupted) {

		// Pass To Super Class
		super.end(interrupted);
		
		// Measure Elapsed Time
		double elapsedTime = Timer.getFPGATimestamp() - startTime;

		// We Shouldn't Be Moving
		drivetrain.stop();

		// Send Data To NT
		data = entries.toString();
		data = data.substring(1, data.length() - 1) + ", ";
		telemetryEntry.setString(data);
		entries.clear();
		System.out.println("Collected : " + counter + " in " + elapsedTime + " seconds");
		data = "";

	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
