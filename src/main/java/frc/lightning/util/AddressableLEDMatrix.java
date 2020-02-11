package frc.lightning.util;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;

public class AddressableLEDMatrix extends LEDMatrixMap{

    AddressableLED led;
    AddressableLEDBuffer buffer;
    HashMap<Integer, Integer[]> ledMap = new HashMap<>();
    ArrayList<Integer[]> columnArray = new ArrayList<>();
    int width, length;
    int chaseInt = 0;
    int ledLength;
    int sideLength; 

    public AddressableLEDMatrix(int _width, int _length, int port){ 
        AddressableLED _led = new AddressableLED(port);
        led = _led;
        width = _width;
        length = _length;
        ledLength = _length * _width;
        buffer = new AddressableLEDBuffer(ledLength);
        led.setLength(ledLength);
        setArray();
        setPreMap();
        led.start();
    }

    /*
    Store LED Matrix into coordinate grids
    */
    private void setArray(){ 
        for(int i = 0; i < width; i ++){
            Integer[] rowArray = new Integer[32];
            for(int t = 0; t < length; t ++){
                if(i%2 == 0){rowArray[t] = (i * length) + t; }
                 else {rowArray[t] = ((i+1) * length) - (t+1);}
            }
            columnArray.add(rowArray);
        }

        for(int n = 0; n < columnArray.size(); n ++){
            ledMap.put(n, columnArray.get(n));
        }
    }

    public void setColor(int row, int column, int r, int g, int b){
        buffer.setLED(ledMap.get(row - 1)[column - 1], new Color(r, g, b));
        led.setData(buffer);
        System.out.println(ledMap.get(row - 1)[column - 1]);
    }

    public void setColor(int[] rows, int column, int r, int g, int b){
        for(int i = 0 ; i < rows.length; i ++){
            buffer.setLED(ledMap.get(rows[i] - 1)[column - 1], new Color(r, g, b));
            led.setData(buffer);
        }
    }

    public void setColor(int row, int[] columns, int r, int g, int b){
        for(int i = 0 ; i < columns.length; i ++){
            buffer.setLED(ledMap.get(row - 1)[columns[i] - 1], new Color(r, g, b));
            led.setData(buffer);
        }
    }

    public void clearColor(){
        for(int i = 0; i < ledLength; i ++){
            buffer.setHSV(i, 0,0,0);
            led.setData(buffer);
        }
    }


    public ArrayList<HashMap<Integer, Integer[]>> setLedMapCollection(HashMap<Integer, Integer[]>... patternMap){
        ArrayList<HashMap<Integer, Integer[]>> ledMapCollection = new ArrayList<>();
        for (HashMap<Integer,Integer[]> ledMap : patternMap) {
            ledMapCollection.add(ledMap);
        }
        return ledMapCollection;
    }

    public void setMap(HashMap<Integer, Integer[]> patternMap, int topLeftRow, int topLeftColumn, int hue, int saturation, int v){
        for(int i = 0; i < patternMap.size(); i ++){
            Timer.delay(0.001);
            for(int patternInt : patternMap.get(i)){
                buffer.setHSV(ledMap.get((topLeftRow-1) + i)[(topLeftColumn-1) + patternInt], hue, saturation, v);
            }
        }
        led.setData(buffer);
    }


    public void setMaps(ArrayList<HashMap<Integer, Integer[]>> patternMaps, int topLeftRow, int topLeftColumn, int h, int s, int v, int space){
        int _patternInt = 0;
        int startPoint = 0;
        for(int t = 0; t < patternMaps.size(); t ++){
            for(int i = 0; i < patternMaps.get(t).size(); i ++){
            Timer.delay(0.001);
                for(int patternInt : patternMaps.get(i).get(i)){
                    buffer.setHSV(ledMap.get((topLeftRow-1) + i)[(topLeftColumn-1) + patternInt + startPoint], h, s, v);
                    _patternInt = patternInt;
                }
                startPoint += topLeftColumn + _patternInt + space;
            }
       }
        led.setData(buffer);
    }

    public void setChase(Color backgroundColor, Color chaseColor){
        for(int i = 0; i < (width * length); i ++){
            buffer.setLED(i, backgroundColor);
            led.setData(buffer);
        }
        buffer.setLED(chaseInt, chaseColor);
        chaseInt = chaseInt % length;
    }

    public void start(){
        led.start();
    }
    public void stop(){
        led.stop();
    }
}
