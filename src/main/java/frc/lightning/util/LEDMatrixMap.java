package frc.lightning.util;
import java.util.HashMap;

public class LEDMatrixMap {
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
    
    public void setSquareMap(int sideLength){
        this.sideLength = sideLength;
    }

    public void setPreMap(){
        //A
        
        //E
        mapE.put(0, new Integer[]{0,1,2,3,4,5});
        mapE.put(1, new Integer[]{0});
        mapE.put(2, new Integer[]{0,1,2,3,4});
        mapE.put(3, new Integer[]{0});
        mapE.put(4, new Integer[]{0,1,2,3,4,5});
        //G
        mapG.put(0, new Integer[]{0,1,2,3,4});
        mapG.put(1, new Integer[]{0});
        mapG.put(2, new Integer[]{0,3,4});
        mapG.put(3, new Integer[]{0,4});
        mapG.put(4, new Integer[]{0,1,2,3,4});

        //R
        mapR.put(0, new Integer[]{0,1,2,3});
        mapR.put(1, new Integer[]{0,4});
        mapR.put(2, new Integer[]{0,1,2,3});
        mapR.put(3, new Integer[]{0,4});
        mapR.put(4, new Integer[]{0,4});

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

        //Map Lightning Greg Orange
        mapLightningGregOrange.put(0, new Integer[]{5, 6, 7});
        mapLightningGregOrange.put(1, new Integer[]{4, 5, 6, 13, 14, 17, 18, 19, 21, 22, 23, 26, 27});
        mapLightningGregOrange.put(2, new Integer[]{4, 5, 12, 17, 19, 21, 25});
        mapLightningGregOrange.put(3, new Integer[]{4, 5, 12, 17, 18, 21, 22, 23, 25});
        mapLightningGregOrange.put(4, new Integer[]{3, 4, 12, 14, 15, 17, 19, 21, 25, 27, 28});
        mapLightningGregOrange.put(5, new Integer[]{3, 4, 12, 15, 17, 19, 21, 25, 28});
        mapLightningGregOrange.put(6, new Integer[]{2, 3, 4, 5, 6, 7, 8, 13, 14, 17, 19, 21, 22, 23, 26, 27});
        mapLightningGregOrange.put(7, new Integer[]{2, 5, 8, 9});

        //Map Lightning Greg White
        mapLightningGregWhite.put(0, new Integer[]{});
        mapLightningGregWhite.put(1, new Integer[]{3, 7, 8});
        mapLightningGregWhite.put(2, new Integer[]{2, 9});
        mapLightningGregWhite.put(3, new Integer[]{1, 10});
        mapLightningGregWhite.put(4, new Integer[]{1, 10});
        mapLightningGregWhite.put(5, new Integer[]{2, 9});
        mapLightningGregWhite.put(6, new Integer[]{});
        mapLightningGregWhite.put(7, new Integer[]{});

        Integer[] inte = new Integer[8];
        for(int i = 0; i < inte.length; i ++){
            inte[i] = i;
        }
        for(int y = 0; y < inte.length; y ++){
            mapSquare.put(y, inte);
        }
        
        
    
}
}