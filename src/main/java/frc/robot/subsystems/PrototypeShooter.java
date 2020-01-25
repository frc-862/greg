package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lightning.LightningRobot;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.misc.REVGains;

public class PrototypeShooter extends SubsystemBase {

    private VictorSPX pmotor1;
    private VictorSPX pmotor2;
    private VictorSPX pmotor3;
    private Encoder pmotor1encoder;
    private Encoder pmotor2encoder;
    private Encoder pmotor3encoder;
    private PIDController motor1PIDContoller;
    private PIDController motor2PIDContoller;
    private PIDController motor3PIDContoller;
    private boolean atVel = false;


    public PrototypeShooter() {
        CommandScheduler.getInstance().registerSubsystem(this);

        pmotor1 = new WPI_VictorSPX(7);
        pmotor2 = new WPI_VictorSPX(8);
        pmotor3 = new WPI_VictorSPX(15);
        pmotor1.setInverted(true);
        pmotor2.setInverted(true);
        pmotor3.setInverted(true);
        pmotor1encoder = new Encoder(0,1,true);
        pmotor2encoder = new Encoder(2,3,true);
        pmotor3encoder = new Encoder(4,5,true);
//        pmotor1encoder.setDistancePerPulse(60/2048);
//        pmotor2encoder.setDistancePerPulse(60/2048);
//        pmotor3encoder.setDistancePerPulse(60/2048);
        motor1PIDContoller = new PIDController(Constants.PShooterKp,Constants.PShooterKi,Constants.PShooterKd);
        motor2PIDContoller = new PIDController(Constants.PShooterKp,Constants.PShooterKi,Constants.PShooterKd);
        motor3PIDContoller = new PIDController(Constants.PShooterKp,Constants.PShooterKi,Constants.PShooterKd);
        SmartDashboard.putData("Shooter", this);
        SmartDashboard.putNumber("Shooter RPM", 0);


        System.out.println("Shooter Config");
    }
    @Override
    public void periodic() {
        // super.periodic();

        double rate1 = pmotor1encoder.getRate() * 60 / 2048;
        double output1 = Constants.M1ShooterKf * motor1PIDContoller.getSetpoint() + motor1PIDContoller.calculate(rate1);
        pmotor1.set(ControlMode.PercentOutput, output1);

        double rate2 = pmotor2encoder.getRate()* 60/2048;
        double output2 = Constants.M2shooterKf * motor2PIDContoller.getSetpoint() + motor2PIDContoller.calculate(rate2);
        pmotor2.set(ControlMode.PercentOutput, output2);

        double rate3 = pmotor3encoder.getRate()* 60/2048;
        double output3 = Constants.M3shooterKf * motor2PIDContoller.getSetpoint() + motor2PIDContoller.calculate(rate3);
        pmotor3.set(ControlMode.PercentOutput, output3);

        SmartDashboard.putNumber("Motor1 RPM", rate1);
        SmartDashboard.putNumber("Motor2 RPM", rate2);
        SmartDashboard.putNumber("Motor3 RPM", rate3);
            if (LightningMath.epsilonEqual(SmartDashboard.getNumber("Shooter RPM", 0),rate1,500)&&
                LightningMath.epsilonEqual(SmartDashboard.getNumber("Shooter RPM", 0),rate2,500)&&
                LightningMath.epsilonEqual(SmartDashboard.getNumber("Shooter RPM", 0),rate3,500)
                ){
            atVel=true;
        }else{atVel=false;}
            SmartDashboard.putBoolean("READY TO FIRE", atVel);

    }

    public void setVelocityMotor1(double dV) {
        motor1PIDContoller.setSetpoint(dV);

//        double error = dV-pmotor1encoder.getRate();
//        double pValue = error*Constants.PShooterKp;
//        double fValue = dV*Constants.PShooterKf;
//        double power = pValue+fValue;
//        pmotor1.set(ControlMode.PercentOutput,power);

    }
    public void setVelocityMotor2(double dV){
        //double error = dV-pmotor2encoder.getRate();
        //pmotor2.set(ControlMode.PercentOutput,error*Constants.PShooterKp+dV*Constants.PShooterKf);

        motor2PIDContoller.setSetpoint(dV);
    }
    public void setVelocityMotor3(double dV){
        //double error = dV-pmotor3encoder.getRate();
        //pmotor3.set(ControlMode.PercentOutput,error*Constants.PShooterKp+dV*Constants.PShooterKf);

        motor3PIDContoller.setSetpoint(dV);
    }
    public void setVelocityMShootoer(double dV){
        setVelocityMotor1(dV);
        setVelocityMotor2(dV);
        setVelocityMotor3(dV);
    }






}
