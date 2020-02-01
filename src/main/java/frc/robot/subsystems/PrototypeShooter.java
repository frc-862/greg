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
import frc.lightning.logging.DataLogger;
import frc.lightning.util.LightningMath;
import frc.robot.Constants;
import frc.robot.misc.REVGains;

public class PrototypeShooter extends SubsystemBase {
    private final boolean loggingEnabled = true;

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
       // SmartDashboard.putData("Shooter", this);
        SmartDashboard.putNumber("Shooter RPM", 0);

        //System.out.println("Shooter Config");

        DataLogger.addDataElement("motor1rpm", this::getMotor1Rate);
        DataLogger.addDataElement("motor2rpm", this::getMotor2Rate);
        DataLogger.addDataElement("motor3rpm", this::getMotor3Rate);
        DataLogger.addDataElement("setSpeed1", this::getsetpower1);
        DataLogger.addDataElement("setSpeed2", this::getsetpower2);
        DataLogger.addDataElement("setSpeed3", this::getsetpower3);
        DataLogger.addDataElement("P value", this::getPValue);
        DataLogger.addDataElement("I value", this::getIValue);
        DataLogger.addDataElement("D value", this::getDValue);
        DataLogger.addDataElement("M1 FF value", this::getFFValue1);
        DataLogger.addDataElement("M2 FF value", this::getFFValue2);
        DataLogger.addDataElement("M3 FF value", this::getFFValue3);
        // DataLogger.addDataElement("motor1ff", () -> );
//        SmartDashboard.putData("Speed controler 1",motor1PIDContoller);
//        SmartDashboard.putData("Speed controler 2",motor2PIDContoller);
//        SmartDashboard.putData("Speed controler 3",motor3PIDContoller);
    }

    public double getMotor1Rate(){
        return pmotor1encoder.getRate() * 60 / 2048;
    }

    public double getMotor2Rate(){
        return pmotor2encoder.getRate() * 60 / 2048;
    }

    public double getMotor3Rate(){
        return pmotor3encoder.getRate() * 60 / 2048;
    }

    @Override
    public void periodic() {
        // super.periodic();
        SmartDashboard.getNumber("Shooter RPM", 0);

        double rate1 = getMotor1Rate();
        double output1 = Constants.M1ShooterKf * motor1PIDContoller.getSetpoint() + motor1PIDContoller.calculate(rate1);
        SmartDashboard.putNumber("output1", output1);
        pmotor1.set(ControlMode.PercentOutput, output1);

        double rate2 = getMotor2Rate();
        double output2 = Constants.M2shooterKf * motor2PIDContoller.getSetpoint() + motor2PIDContoller.calculate(rate2);
        pmotor2.set(ControlMode.PercentOutput, output2);

        double rate3 = getMotor3Rate();
        double output3 = Constants.M3shooterKf * motor3PIDContoller.getSetpoint() + motor3PIDContoller.calculate(rate3);
        pmotor3.set(ControlMode.PercentOutput, output3);

        SmartDashboard.putNumber("Motor1 RPM", rate1);
        SmartDashboard.putNumber("Motor2 RPM", rate2);
        SmartDashboard.putNumber("Motor3 RPM", rate3);
        SmartDashboard.putNumber("Motor1 Error", motor1PIDContoller.getSetpoint() - rate1);
        SmartDashboard.putNumber("Motor2 Error", motor2PIDContoller.getSetpoint() - rate2);
        SmartDashboard.putNumber("Motor3 Error", motor3PIDContoller.getSetpoint() - rate3);
        SmartDashboard.putNumber("Motor1 Set point", motor1PIDContoller.getSetpoint());
        SmartDashboard.putNumber("Motor3 set point", motor3PIDContoller.getSetpoint());
            if (LightningMath.epsilonEqual(motor1PIDContoller.getSetpoint(),rate1,200)&&
                LightningMath.epsilonEqual(motor2PIDContoller.getSetpoint(),rate2,200)&&
                LightningMath.epsilonEqual(motor3PIDContoller.getSetpoint(),rate3,200)
                ){
            atVel=true;
        }else{atVel=false;}
            SmartDashboard.putBoolean("READY TO FIRE", atVel);

    }
    public double getsetpower1(){
        return motor1PIDContoller.getSetpoint();
    }
    public double getsetpower2(){
        return motor2PIDContoller.getSetpoint();
    }
    public double getsetpower3(){
        return motor3PIDContoller.getSetpoint();
    }

    public void setVelocityMotor1(double dV) {
        motor1PIDContoller.setSetpoint(dV-900);//800

//        double error = dV-pmotor1encoder.getRate();
//        double pValue = error*Constants.PShooterKp;
//        double fValue = dV*Constants.PShooterKf;
//        double power = pValue+fValue;
//        pmotor1.set(ControlMode.PercentOutput,power);

    }
    public void setVelocityMotor2(double dV){
        //double error = dV-pmotor2encoder.getRate();
        //pmotor2.set(ControlMode.PercentOutput,error*Constants.PShooterKp+dV*Constants.PShooterKf);

        motor2PIDContoller.setSetpoint(dV-900);//800
    }
    public void setVelocityMotor3(double dV){
        //double error = dV-pmotor3encoder.getRate();
        //pmotor3.set(ControlMode.PercentOutput,error*Constants.PShooterKp+dV*Constants.PShooterKf);

        motor3PIDContoller.setSetpoint(dV+650);
    }
    public void setVelocityMShootoer(double dV){
        setVelocityMotor1(dV);
        setVelocityMotor2(dV);
        setVelocityMotor3(dV);
    }
    public double getPValue(){
        return Constants.PShooterKp;
    }
    public double getIValue(){
        return Constants.PShooterKi;
    }
    public double getDValue(){
        return Constants.PShooterKd;
    }
    public double getFFValue1(){
        return Constants.M1ShooterKf;
    }
    public double getFFValue2(){
        return Constants.M2shooterKf;
    }
    public double getFFValue3(){
        return Constants.M3shooterKf;
    }






}
