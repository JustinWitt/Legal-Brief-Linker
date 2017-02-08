/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Justin
 */
public class Coordinates {
    private float lowerLeftX;
    private float lowerLeftY;
    private float upperRightX;
    private float upperRightY;
    private float splitLowerLeftX;
    private float splitLowerLeftY;
    private float splitUpperRightX;
    private float splitUpperRightY;

    
    
    public Coordinates(){}
    
    public Coordinates(float lLX, float lLY, float uRX, float uRY, 
            float sLLX, float sLLY, float sURX, float sURY){
               
        this.lowerLeftX = lLX;
        this.lowerLeftY = lLY;
        this.upperRightX = uRX;
        this.upperRightY = uRY;
        this.splitLowerLeftX = sLLX;
        this.splitLowerLeftY = sLLY;
        this.splitUpperRightX = sURX;
        this.splitUpperRightY = sURY;
    }

    public float getLowerLeftX() {
        return lowerLeftX;
    }

    public void setLowerLeftX(float lowerLeftX) {
        this.lowerLeftX = lowerLeftX;
    }

    public float getLowerLeftY() {
        return lowerLeftY;
    }

    public void setLowerLeftY(float lowerLeftY) {
        this.lowerLeftY = lowerLeftY;
    }

    public float getUpperRightX() {
        return upperRightX;
    }

    public void setUpperRightX(float upperRightX) {
        this.upperRightX = upperRightX;
    }

    public float getUpperRightY() {
        return upperRightY;
    }

    public void setUpperRightY(float upperRightY) {
        this.upperRightY = upperRightY;
    }

    public float getSplitLowerLeftX() {
        return splitLowerLeftX;
    }

    public void setSplitLowerLeftX(float splitLowerLeftX) {
        this.splitLowerLeftX = splitLowerLeftX;
    }

    public float getSplitLowerLeftY() {
        return splitLowerLeftY;
    }

    public void setSplitLowerLeftY(float splitLowerLeftY) {
        this.splitLowerLeftY = splitLowerLeftY;
    }

    public float getSplitUpperRightX() {
        return splitUpperRightX;
    }

    public void setSplitUpperRightX(float splitUpperRightX) {
        this.splitUpperRightX = splitUpperRightX;
    }

    public float getSplitUpperRightY() {
        return splitUpperRightY;
    }

    public void setSplitUpperRightY(float splitUpperRightY) {
        this.splitUpperRightY = splitUpperRightY;
    }
    
    public float[] getAll(){
        float[] all = {lowerLeftX, lowerLeftY, upperRightX, upperRightY,
        splitLowerLeftX, splitLowerLeftY, splitUpperRightX, splitUpperRightY};
        return all;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "lowerLeftX=" + lowerLeftX + ", lowerLeftY=" + lowerLeftY + ", upperRightX=" + upperRightX + ", upperRightY=" + upperRightY + ", splitLowerLeftX=" + splitLowerLeftX + ", splitLowerLeftY=" + splitLowerLeftY + ", splitUpperRightX=" + splitUpperRightX + ", splitUpperRightY=" + splitUpperRightY + '}';
    }
    
    
}
