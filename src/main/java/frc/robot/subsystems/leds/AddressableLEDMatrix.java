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
    Integer[] widths;
    Integer[] lengths;
    int ledLengths;
    int chaseInt = 0;
    int sideLength; 

    public AddressableLEDMatrix(int matrixWidth1, int matrixLength1, int port){ 
        led = new AddressableLED(port);
        widths[0] = matrixWidth1;
        lengths[0] = matrixLength1;
        ledLengths = matrixLength1 * matrixWidth1;
        buffer = new AddressableLEDBuffer(ledLengths);
        led.setLength(ledLengths);
        setArray(); 
        setPreMap();
        led.start();
    }

    public AddressableLEDMatrix(Integer matrixWidth[], Integer matrixLength[], int port){ 
        led = new AddressableLED(port);

        for(int i = 0; i < matrixWidth.length; i ++){
            ledLengths += matrixLength[i] * matrixWidth[i];
        }

        widths = matrixWidth;
        lengths = matrixLength;
        buffer = new AddressableLEDBuffer(ledLengths);
        led.setLength(ledLengths);
        setArrays(); 
        setPreMap();
        led.start();
    }

    /*
    Store LED Matrix into coordinate grids
    */
    private void setArray(){
        for(int i = 0; i < widths[0]; i ++){ // cycle through row number
            Integer[] rowArray = new Integer[lengths[0]]; 
            for(int t = 0; t < lengths[0]; t ++){ // cycle through column number
                if(i%2 == 0){rowArray[t] = (i * lengths[0]) + t; } 
                else {rowArray[t] = ((i+1) * lengths[0]) - (t+1);}
            }
            ledMap.put(i, rowArray);
        }
    }

    private void setArrays(){ 
        int startingPoint = 0;
        for(int i = 0; i < widths.length; i ++){
            startingPoint = i != 0 ? startingPoint + widths[i-1] * lengths[i-1] : 0;
        
            for(int n = 0; n < widths[i]; n ++){
                Integer[] rowArray = new Integer[lengths[i]]; 
        
                for(int t = 0; t < lengths[i]; t ++){ // cycle through column number
                    if(n%2 == 0){rowArray[t] = (startingPoint + (n * lengths[i])) + t; } 
                    else {rowArray[t] = (startingPoint + ((n+1) * lengths[i])) - (t+1);}
                }
            columnArray.add(rowArray);
            }   
        }
        
        for(int x = 0; x < columnArray.size(); x++){
            ledMap.put(x, columnArray.get(x));
        }
    }

    public void setColor(int row, int column, int h, int s, int v, int matrix){
        int startingRow = widths[matrix - 1];
        Timer.delay(0.001);
        buffer.setHSV(ledMap.get(row - 1 + startingRow)[column - 1], h, s, v);
        led.setData(buffer);
        //System.out.println(ledMap.get(row - 1)[column - 1]);
    }

    public void setColor(int row, Integer[] columns, int h, int s, int v, int matrix){
        int startingRow = widths[matrix - 1];
         for(int i = 0 ; i < columns.length; i ++){
            Timer.delay(0.001);
            buffer.setHSV(ledMap.get(row - 1 + startingRow)[columns[i] - 1], h, s, v);
            }
         led.setData(buffer);
    }

    public void setColor(Integer row[], Integer[] columns, int h, int s, int v, int matrix){
        int startingRow = widths[matrix - 1];
        for(int t = 0; t < row.length; t ++){
         for(int i = 0 ; i < columns.length; i ++){
            Timer.delay(0.001);
            buffer.setHSV(ledMap.get(t + startingRow)[columns[i] - 1], h, s, v);
            }
        }
         led.setData(buffer);
    }
    

    public void clearColor(){
        for(int i = 0; i < ledLengths; i ++){
            Timer.delay(0.001);
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
        for(int i = 0; i < matrixNumber; i ++){
            matrixPixel += widths[i] * lengths[i];
        }

        for(int t = 0; t < matrixPixel; t ++){
            buffer.setHSV(t, h, s, v);      
        }
       led.setData(buffer);
    }

    public void setMap(HashMap<Integer, Integer[]> patternMap, int topLeftRow, int topLeftColumn, int hue, int saturation, int v){
        for(int i = 0; i < patternMap.size(); i ++){
            Timer.delay(0.001);
            for(int patternInt : patternMap.get(i)){
                buffer.setHSV(ledMap.get((topLeftRow - 1) + i)[(topLeftColumn - 1) + patternInt], hue, saturation, v);
            }
        }
        led.setData(buffer);
    }

    public void setMap(HashMap<Integer, Integer[]> patternMap, int topLeftRow, int topLeftColumn, int hue, int saturation, int v, int matrix){
        int startingRow = widths[matrix - 1];
        for(int i = 0; i < patternMap.size(); i ++){
            Timer.delay(0.001);
            for(int patternInt : patternMap.get(i)){
                buffer.setHSV(ledMap.get(startingRow + (topLeftRow - 1) + i)[(topLeftColumn - 1) + patternInt], hue, saturation, v);
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
