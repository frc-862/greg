package frc.robot.subsystems.leds;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ser.std.MapProperty;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class AddressableLEDMatrix extends LEDMatrixMap{

    AddressableLED led = null;
    AddressableLEDBuffer buffer;
    HashMap<Integer, Integer[]> ledMap = new HashMap<>();
    ArrayList<Integer[]> columnArray = new ArrayList<>();
    int width1, length1, width2, length2;
    int chaseInt = 0;
    int ledLength1, ledLength2;
    int sideLength; 

    public AddressableLEDMatrix(int matrixWidth1, int matrixLength1, int port){ 

        led = new AddressableLED(port);
        width1 = matrixWidth1;
        length1 = matrixLength1;
        ledLength1 = matrixLength1 * matrixWidth1;
        buffer = new AddressableLEDBuffer(ledLength1);
        led.setLength(ledLength1);
        setArray(); 
        setPreMap();
        led.start();
    }

    public AddressableLEDMatrix(int matrixWidth1, int matrixLength1, int matrixWidth2, int matrixLength2, int port){ 
        led = new AddressableLED(port);
        width1 = matrixWidth1;
        width2 = matrixWidth2;
        length1 = matrixLength1;
        length2 = matrixLength2;
        ledLength1 = matrixLength1 * matrixWidth1;
        ledLength2 = matrixWidth2 * matrixLength2;
        buffer = new AddressableLEDBuffer(ledLength1 + ledLength2);
        led.setLength(ledLength1 + ledLength2);
        setArray(); 
        setPreMap();
        led.start();
    }

    /*
    Store LED Matrix into coordinate grids
    */
    private void setArray(){ 
        if(ledLength1 != 0){ // matrix #1
            for(int i = 0; i < width1; i ++){ // cycle through row number
                Integer[] rowArray = new Integer[length1]; 
                for(int t = 0; t < length1; t ++){ // cycle through column number
                    if(i%2 == 0){rowArray[t] = (i * length1) + t; } 
                    else {rowArray[t] = ((i+1) * length1) - (t+1);}
            }
            columnArray.add(rowArray);
            }
        }
        if(ledLength2 != 0){ // matrix #2
             for(int i = 0; i < width2; i ++){
                 Integer[] rowArray = new Integer[length2];
                 for(int t = 0; t < length2; t ++){
                    if(i%2 == 0){rowArray[t] = (ledLength1 +  (i * length2)) + t; }
                     else {rowArray[t] = (ledLength1 + ((i+1) * length2)) - (t+1);}
                 }
            columnArray.add(rowArray);
             }
        }
        // put all saved coordinates into grid
        for(int n = 0; n < columnArray.size(); n ++){
             ledMap.put(n, columnArray.get(n));
        }
    }

    public void setColor(int row, int column, int h, int s, int v){
        buffer.setHSV(ledMap.get(row - 1)[column - 1], h, s, v);
        led.setData(buffer);
        //System.out.println(ledMap.get(row - 1)[column - 1]);
    }

    public void setColor(int[] rows, int column, int h, int s, int v){
        for(int i = 0 ; i < rows.length; i ++){
            buffer.setHSV(ledMap.get(rows[i] - 1)[column - 1], h, s, v);
            led.setData(buffer);
        }
    }

    public void setColor(int row, int[] columns, int h, int s, int v){
        for(int i = 0 ; i < columns.length; i ++){
            buffer.setHSV(ledMap.get(row - 1)[columns[i] - 1], h, s, v);
            led.setData(buffer);
        }
    }

    public void clearColor(int ledLength){
        for(int i = 0; i < ledLength; i ++){
            Timer.delay(0.01);
            buffer.setHSV(i, 0,0,0);
            led.setData(buffer);
        }
    }
    public void setRange (int startPixel, int endPixel, int h, int s, int v){
        for(int t = startPixel - 1; t < endPixel - startPixel; t ++){
            buffer.setHSV(t, h, s, v);
           led.setData(buffer);
         }
    }

    public void setAll (int h, int s, int v, int matrixNumber){
        int matrixPixel = 0;
        matrixPixel = matrixNumber == 1 ? ledLength1 : matrixNumber == 2 ? ledLength2 : matrixNumber == 3 ? ledLength2 + ledLength1 : 0;
        for(int t = 0; t < matrixPixel; t ++){
            buffer.setHSV(t, h, s, v);       System.out.println(matrixPixel);
        }
       led.setData(buffer);
    }

    public void setMatrix2 (int startPixel, int endPixel, int h, int s, int v){
        for(int t = startPixel - 1; t < endPixel - startPixel; t ++){
            buffer.setHSV(t, h, s, v);
           led.setData(buffer);
         }
    }

    public void clearColor(){
        for(int i = 0; i < ledLength1 + ledLength2; i ++){
            buffer.setHSV(i, 0,0,0);
            led.setData(buffer);
        }
    }


    public void setMap(HashMap<Integer, Integer[]> patternMap, int topLeftRow, int topLeftColumn, int hue, int saturation, int v){
        for(int i = 0; i < patternMap.size(); i ++){
            Timer.delay(0.001);
            for(int patternInt : patternMap.get(i)){
                buffer.setHSV(ledMap.get((topLeftRow) + i)[(topLeftColumn) + patternInt], hue, saturation, v);
                //System.out.println(topLeftRow);
                //System.out.println(topLeftColumn);
            }
        }
        led.setData(buffer);
    }


    //write letter with word
    public void setWord(String word, int row, int column, int h, int s, int v){
        char[] letters = word.toCharArray();
        ArrayList<HashMap<Integer, Integer[]>> mapArray = new ArrayList<HashMap<Integer, Integer[]>>();
        for(char hfdhgd:letters){
            switch(hfdhgd) {
                case 'a': mapArray.add(mapA);
                case 'b': mapArray.add(mapB);
                case 'c': mapArray.add(mapC);
                case 'd': mapArray.add(mapD);
                case 'e': mapArray.add(mapE);
                case 'f': mapArray.add(mapF);
                case 'g': mapArray.add(mapG);
                case 'h': mapArray.add(mapH);
                case 'i': mapArray.add(mapI);
                case 'j': mapArray.add(mapJ);
                case 'k': mapArray.add(mapK);
                case 'l': mapArray.add(mapL);
                case 'm': mapArray.add(mapM);
                case 'n': mapArray.add(mapN);
                case 'o': mapArray.add(mapO);
                case 'p': mapArray.add(mapP);
                case 'q': mapArray.add(mapQ);
                case 'r': mapArray.add(mapR);
                case 's': mapArray.add(mapS);
                case 't': mapArray.add(mapT);
                case 'u': mapArray.add(mapU);
                case 'v': mapArray.add(mapV);
                case 'w': mapArray.add(mapW);
                case 'x': mapArray.add(mapX);
                case 'y': mapArray.add(mapY);
                case 'z': mapArray.add(mapZ);
            }
        }
        
        
        for(HashMap<Integer, Integer[]> map : mapArray){
            setMap(map, row, column, h, s, v);
        }
        
        
        for(HashMap<Integer, Integer[]> map : mapArray){
        for(int i = 0; i < map.size(); i ++){
            Timer.delay(0.001);
            for(int patternInt : map.get(i)){
                buffer.setHSV(ledMap.get((row-1) + i)[(column-1) + patternInt], h, s, v);
            }
        }
        led.setData(buffer);
    }
    }

    public void start(){
        led.start();
    }
    public void stop(){
        led.stop();
    }
    
}
