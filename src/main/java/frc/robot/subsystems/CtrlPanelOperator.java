/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.LightningColorSensor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DriverStation;

import java.util.Map;

public class CtrlPanelOperator extends SubsystemBase {
    private final Color kBlueTarget = ColorMatch.makeColor(0.17, 0.435, 0.4);
    private final Color kGreenTarget = ColorMatch.makeColor(0.22, 0.526, 0.253);
    private final Color kRedTarget = ColorMatch.makeColor(0.435, 0.401, 0.163);
    private final Color kYellowTarget = ColorMatch.makeColor(0.33, 0.525, 0.14);
    private final Color kFalseGreenTarget = ColorMatch.makeColor(.322, 0.46, 0.21);
    private final Color kFalseYellowTarget = ColorMatch.makeColor(.312, 0.469, 0.219);
    private int color = 0;
    private  int targetColor;
    VictorSP spinner = new VictorSP(RobotMap.CONTROL_PANEL); // 0?
    LightningColorSensor I_Can_Taste_Color = new LightningColorSensor(I2C.Port.kOnboard);
    ColorMatch colorMatch = new ColorMatch();

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

    //Components

    /**
     * Creates a new CtrlPanelOperator.
     */
    public CtrlPanelOperator() {
        //Init
        CommandScheduler.getInstance().registerSubsystem(this);
        colorMatch.addColorMatch(kBlueTarget);
        colorMatch.addColorMatch(kGreenTarget);
        colorMatch.addColorMatch(kRedTarget);
        colorMatch.addColorMatch(kYellowTarget);

        final var tab = Shuffleboard.getTab("ControlPanel");
        tab.addNumber("Blue", this::getColorSensorBlue);
        tab.addNumber("Red", this::getColorSensorRed);
        tab.addNumber("Green", this::getColorSensorGreen);
        tab.addNumber("Color", () -> color);

        final var colors = tab.getLayout("Colors", BuiltInLayouts.kList);
        colors.addBoolean("Yellow", () -> color == 3)
                .withProperties(Map.of("colorWhenTrue", "yellow", "colorWhenFalse", "white"));
        colors.addBoolean("Red", () -> color == 2)
                .withProperties(Map.of("colorWhenTrue", "red", "colorWhenFalse", "white"));
        colors.addBoolean("Green", () -> color == 1)
                .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "white"));
        colors.addBoolean("Blue", () -> color == 0)
                .withProperties(Map.of("colorWhenTrue", "blue", "colorWhenFalse", "white"));
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        Color detectedColor = I_Can_Taste_Color.getColor();

        /**
         * Run the color match algorithm on our detected color
         */
        String colorString;
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

    private String getCurrentColor() {
        return null;
    }


}
