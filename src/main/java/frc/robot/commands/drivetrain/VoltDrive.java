package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lightning.LightningConfig;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.util.JoystickFilter;
import frc.robot.Constants;

public class VoltDrive extends CommandBase {

    LightningDrivetrain drivetrain;
    DoubleSupplier left;
    DoubleSupplier right;
    private double deadband = 0.2;
    private double minPower = 0.1;
    private double maxPower = 1.0;
    private final JoystickFilter filter = new JoystickFilter(deadband, minPower, maxPower, JoystickFilter.Mode.CUBED);

    NetworkTableEntry driveSpeedMult;

    public VoltDrive(LightningDrivetrain drivetrain, DoubleSupplier left, DoubleSupplier right) {
        this.drivetrain = drivetrain;
        this.left = left;
        this.right = right;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        ShuffleboardTab tab = Shuffleboard.getTab("Demo");
        boolean alreadyAdded = false;
        for(var comp : tab.getComponents()){
            if(comp.getTitle().equals("Speed Muliplier demo")){
                alreadyAdded = true;
                 
            }
        }

        if(!alreadyAdded){
                  driveSpeedMult = tab.add("Speed Multiplier demo", Constants.SLOWMODEMULT).getEntry();
        }
  
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double leftVolts = filter.filter(driveSpeedMult.getDouble(Constants.SLOWMODEMULT) * left.getAsDouble());
        double rightVolts = filter.filter(driveSpeedMult.getDouble(Constants.SLOWMODEMULT) * right.getAsDouble());

        leftVolts  *= LightningConfig.VOLT_LIMIT;
        rightVolts *= LightningConfig.VOLT_LIMIT;

        drivetrain.setOutput(leftVolts, rightVolts);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        drivetrain.stop();
    }

}