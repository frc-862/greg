/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.misc.LightningColorSensor;
import edu.wpi.first.wpilibj.DriverStation;

public class CtrlPanelOperator extends SubsystemBase {

    private final Color kBlueTarget = ColorMatch.makeColor(0.17, 0.435, 0.4);
    private final Color kGreenTarget = ColorMatch.makeColor(0.22, 0.526, 0.253);
    private final Color kRedTarget = ColorMatch.makeColor(0.435, 0.401, 0.163);
    private final Color kYellowTarget = ColorMatch.makeColor(0.33, 0.525, 0.14);
    private final Color kFalseGreenTarget = ColorMatch.makeColor(.322, 0.46, 0.21);
    private final Color kFalseYellowTarget = ColorMatch.makeColor(.312, 0.469, 0.219);

    private int color = 0;
    private  int targetColor;

    private VictorSP spinner = new VictorSP(RobotMap.CONTROL_PANEL);
    private LightningColorSensor I_Can_Taste_Color = new LightningColorSensor(I2C.Port.kOnboard);
    private ColorMatch colorMatch = new ColorMatch();

    public enum Colors {
        RED("R", 1),
        GREEN("G", 2),
        BLUE("B", 3),
        YELLOW("Y", 4);

        final String FMSVal;
        final int order;

        private Colors(String FMSVal, int order) {
            this.FMSVal = FMSVal;
            this.order = order;
        }

    }

    public CtrlPanelOperator() {
        CommandScheduler.getInstance().registerSubsystem(this);
        colorMatch.addColorMatch(kBlueTarget);
        colorMatch.addColorMatch(kGreenTarget);
        colorMatch.addColorMatch(kRedTarget);
        colorMatch.addColorMatch(kYellowTarget);
    }

    @Override
    public void periodic() {
        Shuffleboard.getTab("ColorWheel").add("Blue",getColorSensorBlue());
        Shuffleboard.getTab("ColorWheel").add("Red",getColorSensorRed());
        Shuffleboard.getTab("ColorWheel").add("Green",getColorSensorGreen());
        Shuffleboard.getTab("ColorWheel").add("color",color);
        Color detectedColor = I_Can_Taste_Color.getColor();

        // Run the color match algorithm on our detected color
        ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);
        if (match.color == kBlueTarget ||match.color == kFalseGreenTarget) {
            color=0;
        } else if (match.color == kRedTarget || match.color == kFalseYellowTarget) {
            color=2;
        } else if (match.color == kGreenTarget) {
            color=1;
        } else if (match.color == kYellowTarget) {
            color=3;
        } else {
            color=-1;
        }
        //Blue=1
        //red=2
        //green=3
        //yellow=4
        //???=0
        
        Shuffleboard.getTab("ColorWheel").add("is Yellow",color==3);
        Shuffleboard.getTab("ColorWheel").add("is Red",color==2);
        Shuffleboard.getTab("ColorWheel").add("is Green",color==1);
        Shuffleboard.getTab("ColorWheel").add("is Blue",color==0);
    }

    /**
     * spin 3-5 times
     * @return
     */
    public double getColorSensorRed(){
        return I_Can_Taste_Color.getColor().red;
    }

    public double getColorSensorBlue(){
        return I_Can_Taste_Color.getColor().blue;
    }

    public double getColorSensorGreen(){
        return I_Can_Taste_Color.getColor().green;
    }

    public int currentColor(){
        return color;
    }


    public void setPower(double pwr){
        spinner.set(pwr);
    }
    
    public void spin() {

    }

    /**
     * position to fms deginated color
     */
    public void position() {

    }

    public int getFMSMsg() {
        String gameData;
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if(gameData.length() > 0)
        {
            switch (gameData.charAt(0))
            {
                case 'B' :
                    targetColor = 0;
                    //Blue case code
                    break;
                case 'G' :
                    targetColor = 1;
                    //Green case code
                    break;
                case 'R' :
                    targetColor = 2;
                    //Red case code
                    break;
                case 'Y' :
                    targetColor = 3;
                    //Yellow case code
                    break;
                default :
                    //This is corrupt data
                    break;
            }
        } else {
            targetColor = -1;
            //Code for no data received yet
        }
        return targetColor;
    }

}
