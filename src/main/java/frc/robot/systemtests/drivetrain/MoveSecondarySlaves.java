package frc.robot.systemtests.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.revrobotics.CANSparkMax;

import frc.lightning.subsystems.CTREDrivetrain;
import frc.lightning.subsystems.LightningDrivetrain;
import frc.lightning.subsystems.NeoDrivetrain;
import frc.lightning.testing.AbstractTimedSystemTest;
import frc.lightning.fault.FaultCode;

public class MoveSecondarySlaves extends AbstractTimedSystemTest {
    private final double testSpeed = 0.25;
    private static final double testLength = 1.0;
    private final LightningDrivetrain drivetrain;
    private double leftStartPosition;
    private double rightStartPosition;

    CANSparkMax leftNeo;
    CANSparkMax rightNeo;

    BaseMotorController leftMotor;
    BaseMotorController rightMotor;

    public MoveSecondarySlaves(LightningDrivetrain drivetrain) {
       super("", testLength, FaultCode.Codes.DRIVE_SECONDARY_SLAVE_ERROR);
       this.drivetrain = drivetrain;
       addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        super.initialize();
        drivetrain.resetDistance();
        leftStartPosition = drivetrain.getLeftDistance();
        rightStartPosition = drivetrain.getRightDistance();
        if(drivetrain instanceof NeoDrivetrain) {
            NeoDrivetrain neoDrivetrain = (NeoDrivetrain) drivetrain;
            neoDrivetrain.freeSlaves();
            leftNeo = neoDrivetrain.getLeftMotors()[2];
            rightNeo = neoDrivetrain.getRightMotors()[2];
        }
        if(drivetrain instanceof CTREDrivetrain) {
            CTREDrivetrain ctreDrivetrain = (CTREDrivetrain) drivetrain;
            leftMotor = ctreDrivetrain.getLeftMotors()[2];
            rightMotor = ctreDrivetrain.getRightMotors()[2];
        }
    }

    @Override
    public void execute() {
        super.execute();
        if(drivetrain instanceof NeoDrivetrain) {
            leftNeo.set(testSpeed);
            rightNeo.set(testSpeed);
        }
        if(drivetrain instanceof CTREDrivetrain) {
            leftMotor.set(ControlMode.PercentOutput, testSpeed);
            rightMotor.set(ControlMode.PercentOutput, testSpeed);            
        }
    }

    @Override
    public boolean didPass() {
        return ((leftStartPosition > drivetrain.getLeftDistance()) && (rightStartPosition > drivetrain.getRightDistance()));
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Left Diff: " + (drivetrain.getLeftDistance() - leftStartPosition));
        System.out.println("Right Diff: " + (drivetrain.getRightDistance() - rightStartPosition));
        drivetrain.stop();
    }
}
