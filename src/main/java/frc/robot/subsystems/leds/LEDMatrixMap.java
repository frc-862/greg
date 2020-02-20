package frc.robot.subsystems.leds;
import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDMatrixMap extends SubsystemBase {
    private int sideLength;

    public static HashMap<Integer, Integer[]> mapA = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapB = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapC = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapD = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapE = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapF = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapG = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapH = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapI = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapJ = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapK = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapL = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapM = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapN = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapO = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapP = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapQ = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapR = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapS = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapT = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapU = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapV = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapW = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapX = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapY = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapZ = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapSquare = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapCreeperGreen = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapCreeperLightGreen = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapCreeperDarkGreen = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapLightningGregOrange = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapLightningGregWhite = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapExclamation = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapPixel = new HashMap<>();
    public static HashMap<Integer, Integer[]> mapPaddle = new HashMap<>();
    public static HashMap<Integer, Integer[]> map0 = new HashMap<>();
    public static HashMap<Integer, Integer[]> map1 = new HashMap<>();
    public static HashMap<Integer, Integer[]> map2 = new HashMap<>();
    public static HashMap<Integer, Integer[]> map3 = new HashMap<>();
    public static HashMap<Integer, Integer[]> map4 = new HashMap<>();
    public static HashMap<Integer, Integer[]> map5 = new HashMap<>();
        
        
    
    
    public void setSquareMap(int sideLength){
        this.sideLength = sideLength;
    }

    public void setPreMap(){
        //A
        mapA.put(0, new Integer[]{2});
        mapA.put(1, new Integer[]{1, 3});
        mapA.put(2, new Integer[]{0, 4});
        mapA.put(3, new Integer[]{0, 4});
        mapA.put(4, new Integer[]{0, 1, 2, 3, 4});
        mapA.put(5, new Integer[]{0, 4});
        mapA.put(6, new Integer[]{0, 4});
        mapA.put(7, new Integer[]{0, 4});

        //B
        mapB.put(0, new Integer[]{0, 1, 2, 3});
        mapB.put(1, new Integer[]{0, 4});
        mapB.put(2, new Integer[]{0, 4});
        mapB.put(3, new Integer[]{0, 1, 2, 3});
        mapB.put(4, new Integer[]{0, 4});
        mapB.put(5, new Integer[]{0, 4});
        mapB.put(6, new Integer[]{0, 4});
        mapB.put(7, new Integer[]{0, 1, 2, 3});

        //C
        mapC.put(0, new Integer[]{0, 1, 2, 3, 4});
        mapC.put(1, new Integer[]{0, 4});
        mapC.put(2, new Integer[]{0});
        mapC.put(3, new Integer[]{0});
        mapC.put(4, new Integer[]{0});
        mapC.put(5, new Integer[]{0});
        mapC.put(6, new Integer[]{0, 4});
        mapC.put(7, new Integer[]{0, 1, 2, 3, 4});

        //D
        mapD.put(0, new Integer[]{0, 1, 2, 3});
        mapD.put(1, new Integer[]{0, 3, 4});
        mapD.put(2, new Integer[]{0, 4});
        mapD.put(3, new Integer[]{0, 4});
        mapD.put(4, new Integer[]{0, 4});
        mapD.put(5, new Integer[]{0, 4});
        mapD.put(6, new Integer[]{0, 3, 4});
        mapD.put(7, new Integer[]{0, 1, 2, 3});

        //E
        mapE.put(0, new Integer[]{0, 1, 2, 3, 4});
        mapE.put(1, new Integer[]{0});
        mapE.put(2, new Integer[]{0});
        mapE.put(3, new Integer[]{0, 1, 2, 3});
        mapE.put(4, new Integer[]{0});
        mapE.put(5, new Integer[]{0});
        mapE.put(6, new Integer[]{0});
        mapE.put(7, new Integer[]{0, 1, 2, 3, 4});

        //F
        mapF.put(0, new Integer[]{0, 1, 2, 3, 4});
        mapF.put(1, new Integer[]{0});
        mapF.put(2, new Integer[]{0});
        mapF.put(3, new Integer[]{0, 1, 2, 3});
        mapF.put(4, new Integer[]{0});
        mapF.put(5, new Integer[]{0});
        mapF.put(6, new Integer[]{0});
        mapF.put(7, new Integer[]{0});
        
        //G
        mapG.put(0, new Integer[]{1, 2, 3});
        mapG.put(1, new Integer[]{0, 1, 3, 4});
        mapG.put(2, new Integer[]{0});
        mapG.put(3, new Integer[]{0});
        mapG.put(4, new Integer[]{0, 2, 3, 4});
        mapG.put(5, new Integer[]{0, 4});
        mapG.put(6, new Integer[]{0, 1, 3, 4});
        mapG.put(7, new Integer[]{1, 2, 3});

        //H
        mapH.put(0, new Integer[]{0, 4});
        mapH.put(1, new Integer[]{0, 4});
        mapH.put(2, new Integer[]{0, 4});
        mapH.put(3, new Integer[]{0, 1, 2, 3, 4});
        mapH.put(4, new Integer[]{0, 4});
        mapH.put(5, new Integer[]{0, 4});
        mapH.put(6, new Integer[]{0, 4});
        mapH.put(7, new Integer[]{0, 4});

        

        //I
        mapI.put(0, new Integer[]{0, 1, 2, 3, 4});
        mapI.put(1, new Integer[]{2});
        mapI.put(2, new Integer[]{2});
        mapI.put(3, new Integer[]{2});
        mapI.put(4, new Integer[]{2});
        mapI.put(5, new Integer[]{2});
        mapI.put(6, new Integer[]{2});
        mapI.put(7, new Integer[]{0, 1, 2, 3, 4});

        //J
        mapJ.put(0, new Integer[]{4});
        mapJ.put(1, new Integer[]{4});
        mapJ.put(2, new Integer[]{4});
        mapJ.put(3, new Integer[]{4});
        mapJ.put(4, new Integer[]{4});
        mapJ.put(5, new Integer[]{4});
        mapJ.put(6, new Integer[]{0, 4});
        mapJ.put(7, new Integer[]{0, 1, 2, 3, 4});

        //K
        mapK.put(0, new Integer[]{0, 4});
        mapK.put(1, new Integer[]{0, 3});
        mapK.put(2, new Integer[]{0, 3});
        mapK.put(3, new Integer[]{0, 1, 2});
        mapK.put(4, new Integer[]{0, 3});
        mapK.put(5, new Integer[]{0, 3});
        mapK.put(6, new Integer[]{0, 4});
        mapK.put(7, new Integer[]{0, 4});

        //L
        mapL.put(0, new Integer[]{0});
        mapL.put(1, new Integer[]{0});
        mapL.put(2, new Integer[]{0});
        mapL.put(3, new Integer[]{0});
        mapL.put(4, new Integer[]{0});
        mapL.put(5, new Integer[]{0});
        mapL.put(6, new Integer[]{0});
        mapL.put(7, new Integer[]{0, 1, 2, 3, 4});

        //M
        mapM.put(0, new Integer[]{0, 4});
        mapM.put(1, new Integer[]{0, 1, 3, 4});
        mapM.put(2, new Integer[]{0, 2, 4});
        mapM.put(3, new Integer[]{0, 4});
        mapM.put(4, new Integer[]{0, 4});
        mapM.put(5, new Integer[]{0, 4});
        mapM.put(6, new Integer[]{0, 4});
        mapM.put(7, new Integer[]{0, 4});

        //N
        mapN.put(0, new Integer[]{0, 4});
        mapN.put(1, new Integer[]{0, 1, 4});
        mapN.put(2, new Integer[]{0, 1, 4});
        mapN.put(3, new Integer[]{0, 2, 4});
        mapN.put(4, new Integer[]{0, 2, 4});
        mapN.put(5, new Integer[]{0, 3, 4});
        mapN.put(6, new Integer[]{0, 3, 4});
        mapN.put(7, new Integer[]{0, 4});

        //O
        mapO.put(0, new Integer[]{1, 2, 3});
        mapO.put(1, new Integer[]{0, 1, 3, 4});
        mapO.put(2, new Integer[]{0, 4});
        mapO.put(3, new Integer[]{0, 4});
        mapO.put(4, new Integer[]{0, 4});
        mapO.put(5, new Integer[]{0, 4});
        mapO.put(6, new Integer[]{0, 1, 3, 4});
        mapO.put(7, new Integer[]{1, 2, 3});

        //P
        mapP.put(0, new Integer[]{0, 1, 2, 3});
        mapP.put(1, new Integer[]{0, 4});
        mapP.put(2, new Integer[]{0, 4});
        mapP.put(3, new Integer[]{0, 1, 2, 3});
        mapP.put(4, new Integer[]{0});
        mapP.put(5, new Integer[]{0});
        mapP.put(6, new Integer[]{0});
        mapP.put(7, new Integer[]{0});

        //Q
        mapQ.put(0, new Integer[]{1, 2, 3});
        mapQ.put(1, new Integer[]{0, 4});
        mapQ.put(2, new Integer[]{0, 4});
        mapQ.put(3, new Integer[]{0, 4});
        mapQ.put(4, new Integer[]{0, 4});
        mapQ.put(5, new Integer[]{0, 2, 4});
        mapQ.put(6, new Integer[]{0, 3});
        mapQ.put(7, new Integer[]{1, 2, 4});
        
        //R
        mapR.put(0, new Integer[]{0, 1, 2, 3});
        mapR.put(1, new Integer[]{0, 4});
        mapR.put(2, new Integer[]{0, 4});
        mapR.put(3, new Integer[]{0, 1, 2, 3});
        mapR.put(4, new Integer[]{0, 4});
        mapR.put(5, new Integer[]{0, 4});
        mapR.put(6, new Integer[]{0, 4});
        mapR.put(7, new Integer[]{0, 4});

        //S
        mapS.put(0, new Integer[]{1, 2, 3, 4});
        mapS.put(1, new Integer[]{0});
        mapS.put(2, new Integer[]{0});
        mapS.put(3, new Integer[]{1, 2, 3});
        mapS.put(4, new Integer[]{4});
        mapS.put(5, new Integer[]{4});
        mapS.put(6, new Integer[]{4});
        mapS.put(7, new Integer[]{0, 1, 2, 3});

        //T
        mapT.put(0, new Integer[]{0, 1, 2, 3, 4});
        mapT.put(1, new Integer[]{2});
        mapT.put(2, new Integer[]{2});
        mapT.put(3, new Integer[]{2});
        mapT.put(4, new Integer[]{2});
        mapT.put(5, new Integer[]{2});
        mapT.put(6, new Integer[]{2});
        mapT.put(7, new Integer[]{2});

        //U
        mapU.put(0, new Integer[]{0, 4});
        mapU.put(1, new Integer[]{0, 4});
        mapU.put(2, new Integer[]{0, 4});
        mapU.put(3, new Integer[]{0, 4});
        mapU.put(4, new Integer[]{0, 4});
        mapU.put(5, new Integer[]{0, 4});
        mapU.put(6, new Integer[]{0, 4});
        mapU.put(7, new Integer[]{1, 2, 3});

        //V
        mapV.put(0, new Integer[]{0, 4});
        mapV.put(1, new Integer[]{0, 4});
        mapV.put(2, new Integer[]{0, 4});
        mapV.put(3, new Integer[]{0, 4});
        mapV.put(4, new Integer[]{0, 4});
        mapV.put(5, new Integer[]{1, 3});
        mapV.put(6, new Integer[]{1, 3});
        mapV.put(7, new Integer[]{2});

        //W
        mapW.put(0, new Integer[]{0, 4});
        mapW.put(1, new Integer[]{0, 4});
        mapW.put(2, new Integer[]{0, 4});
        mapW.put(3, new Integer[]{0, 4});
        mapW.put(4, new Integer[]{0, 2, 4});
        mapW.put(5, new Integer[]{0, 1, 3, 4});
        mapW.put(6, new Integer[]{0, 1, 3, 4});
        mapW.put(7, new Integer[]{0, 4});

        //X
        mapX.put(0, new Integer[]{0, 4});
        mapX.put(1, new Integer[]{1, 3});
        mapX.put(2, new Integer[]{1, 3});
        mapX.put(3, new Integer[]{2});
        mapX.put(4, new Integer[]{1, 3});
        mapX.put(5, new Integer[]{1, 3});
        mapX.put(6, new Integer[]{0, 4});
        mapX.put(7, new Integer[]{0, 4});

        //Y
        mapY.put(0, new Integer[]{0, 4});
        mapY.put(1, new Integer[]{0, 4});
        mapY.put(2, new Integer[]{0, 4});
        mapY.put(3, new Integer[]{0, 1, 2, 3, 4});
        mapY.put(4, new Integer[]{2});
        mapY.put(5, new Integer[]{2});
        mapY.put(6, new Integer[]{2});
        mapY.put(7, new Integer[]{2});

        //Z
        mapZ.put(0, new Integer[]{0, 1, 2, 3, 4});
        mapZ.put(1, new Integer[]{4});
        mapZ.put(2, new Integer[]{3});
        mapZ.put(3, new Integer[]{2});
        mapZ.put(4, new Integer[]{1});
        mapZ.put(5, new Integer[]{0});
        mapZ.put(6, new Integer[]{0});
        mapZ.put(7, new Integer[]{0, 1, 2, 3, 4});

        //!
        mapExclamation.put(0, new Integer[]{2});
        mapExclamation.put(1, new Integer[]{2});
        mapExclamation.put(2, new Integer[]{2});
        mapExclamation.put(3, new Integer[]{2});
        mapExclamation.put(4, new Integer[]{2});
        mapExclamation.put(5, new Integer[]{2});
        mapExclamation.put(6, new Integer[]{});
        mapExclamation.put(7, new Integer[]{2});


        //Map Creeper Green
        mapCreeperGreen.put(0, new Integer[]{2, 4, 5, 6, 7, 8});
        mapCreeperGreen.put(1, new Integer[]{1, 2, 3, 4, 8});
        mapCreeperGreen.put(2, new Integer[]{4, 5});
        mapCreeperGreen.put(3, new Integer[]{1, 5, 8});
        mapCreeperGreen.put(4, new Integer[]{1, 3, 7, 8});
        mapCreeperGreen.put(5, new Integer[]{2});
        mapCreeperGreen.put(6, new Integer[]{1, 2, 7, 8});
        mapCreeperGreen.put(7, new Integer[]{1, 5});
        
        //Map Creeper Light Green
        mapCreeperLightGreen.put(0, new Integer[]{3});
        mapCreeperLightGreen.put(1, new Integer[]{6, 7});
        mapCreeperLightGreen.put(2, new Integer[]{1, 8});
        mapCreeperLightGreen.put(3, new Integer[]{4});
        mapCreeperLightGreen.put(4, new Integer[]{});
        mapCreeperLightGreen.put(5, new Integer[]{1, 7});
        mapCreeperLightGreen.put(6, new Integer[]{});
        mapCreeperLightGreen.put(7, new Integer[]{2, 8});
        
        //Map Creeper Dark Green
        mapCreeperDarkGreen.put(0, new Integer[]{1});
        mapCreeperDarkGreen.put(1, new Integer[]{5});
        mapCreeperDarkGreen.put(2, new Integer[]{});
        mapCreeperDarkGreen.put(3, new Integer[]{});
        mapCreeperDarkGreen.put(4, new Integer[]{2, 6});
        mapCreeperDarkGreen.put(5, new Integer[]{8});
        mapCreeperDarkGreen.put(6, new Integer[]{});
        mapCreeperDarkGreen.put(7, new Integer[]{4, 7});

        //Map Lightning Greg Orange Part
        mapLightningGregOrange.put(0, new Integer[]{5, 6, 7});
        mapLightningGregOrange.put(1, new Integer[]{4, 5, 6, 13, 14, 17, 18, 19, 21, 22, 23, 26, 27});
        mapLightningGregOrange.put(2, new Integer[]{4, 5, 12, 17, 19, 21, 25});
        mapLightningGregOrange.put(3, new Integer[]{4, 5, 12, 17, 18, 21, 22, 23, 25});
        mapLightningGregOrange.put(4, new Integer[]{3, 4, 12, 14, 15, 17, 19, 21, 25, 27, 28});
        mapLightningGregOrange.put(5, new Integer[]{3, 4, 12, 15, 17, 19, 21, 25, 28});
        mapLightningGregOrange.put(6, new Integer[]{2, 3, 4, 5, 6, 7, 8, 13, 14, 17, 19, 21, 22, 23, 26, 27});
        mapLightningGregOrange.put(7, new Integer[]{2, 5, 8, 9});

        //Map Lightning Greg White Part
        mapLightningGregWhite.put(0, new Integer[]{});
        mapLightningGregWhite.put(1, new Integer[]{3, 7, 8});
        mapLightningGregWhite.put(2, new Integer[]{2, 9});
        mapLightningGregWhite.put(3, new Integer[]{1, 10});
        mapLightningGregWhite.put(4, new Integer[]{1, 10});
        mapLightningGregWhite.put(5, new Integer[]{2, 9});
        mapLightningGregWhite.put(6, new Integer[]{});
        mapLightningGregWhite.put(7, new Integer[]{});

        //map pixel
        mapPixel.put(0, new Integer[]{0});
        mapPixel.put(1, new Integer[]{});
        mapPixel.put(2, new Integer[]{});
        mapPixel.put(3, new Integer[]{});
        mapPixel.put(4, new Integer[]{});
        mapPixel.put(5, new Integer[]{});
        mapPixel.put(6, new Integer[]{});
        mapPixel.put(7, new Integer[]{});

        //map 1
        map1.put(0, new Integer[]{2});
        map1.put(1, new Integer[]{1, 2});
        map1.put(2, new Integer[]{0, 2});
        map1.put(3, new Integer[]{2});
        map1.put(4, new Integer[]{2});
        map1.put(5, new Integer[]{2});
        map1.put(6, new Integer[]{2});
        map1.put(7, new Integer[]{2});
        
        //map 2
        map2.put(0, new Integer[]{1, 2, 3});
        map2.put(1, new Integer[]{0, 4});
        map2.put(2, new Integer[]{4});
        map2.put(3, new Integer[]{3});
        map2.put(4, new Integer[]{3});
        map2.put(5, new Integer[]{2});
        map2.put(6, new Integer[]{1});
        map2.put(7, new Integer[]{0, 1, 2, 3, 4});

        //map 3
        map3.put(0, new Integer[]{1, 2, 3});
        map3.put(1, new Integer[]{0, 4});
        map3.put(2, new Integer[]{4});
        map3.put(3, new Integer[]{2, 3});
        map3.put(4, new Integer[]{4});
        map3.put(5, new Integer[]{4});
        map3.put(6, new Integer[]{0, 4});
        map3.put(7, new Integer[]{1, 2, 3});

        //map 4
        map4.put(0, new Integer[]{0, 4});
        map4.put(1, new Integer[]{0, 4});
        map4.put(2, new Integer[]{0, 4});
        map4.put(3, new Integer[]{0, 1, 2, 3, 4});
        map4.put(4, new Integer[]{4});
        map4.put(5, new Integer[]{4});
        map4.put(6, new Integer[]{4});
        map4.put(7, new Integer[]{4});

        //map 5
        map5.put(0, new Integer[]{0, 1, 2, 3, 4});
        map5.put(1, new Integer[]{0});
        map5.put(2, new Integer[]{0});
        map5.put(3, new Integer[]{0, 1, 2, 3});
        map5.put(4, new Integer[]{4});
        map5.put(5, new Integer[]{4});
        map5.put(6, new Integer[]{4});
        map5.put(7, new Integer[]{0, 1, 2, 3});

        //map paddle
        mapPaddle.put(0, new Integer[]{0});
        mapPaddle.put(1, new Integer[]{0});
        mapPaddle.put(2, new Integer[]{0});
        mapPaddle.put(3, new Integer[]{});
        mapPaddle.put(4, new Integer[]{});
        mapPaddle.put(5, new Integer[]{});
        mapPaddle.put(6, new Integer[]{});
        mapPaddle.put(7, new Integer[]{});

        Integer[] inte = new Integer[8];
        for(int i = 0; i < inte.length; i ++){
            inte[i] = i;
        }
        for(int y = 0; y < inte.length; y ++){
            mapSquare.put(y, inte);
        }
        
        
    
}
}