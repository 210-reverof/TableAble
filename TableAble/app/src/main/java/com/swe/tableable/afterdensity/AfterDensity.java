package com.swe.tableable.afterdensity;

public class AfterDensity {
    private int placeNum;
    private String placeName;
    private double currentDensity;
    private int nextTime1;
    private double nextDensity1;
    private int nextTime2;
    private double nextDensity2;

    public AfterDensity(int placeNum, String placeName, double currentDensity, int nextTime1, double nextDensity1, int nextTime2, double nextDensity2) {
        this.placeNum = placeNum;
        this.placeName = placeName;
        this.currentDensity = currentDensity;
        this.nextTime1 = nextTime1;
        this.nextDensity1 = nextDensity1;
        this.nextTime2 = nextTime2;
        this.nextDensity2 = nextDensity2;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(int placeNum) {
        this.placeNum = placeNum;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getCurrentDensity() {
        return currentDensity;
    }

    public void setCurrentDensity(double currentDensity) {
        this.currentDensity = currentDensity;
    }

    public int getNextTime1() {
        return nextTime1;
    }

    public void setNextTime1(int nextTime1) {
        this.nextTime1 = nextTime1;
    }

    public double getNextDensity1() {
        return nextDensity1;
    }

    public void setNextDensity1(double nextDensity1) {
        this.nextDensity1 = nextDensity1;
    }

    public int getNextTime2() {
        return nextTime2;
    }

    public void setNextTime2(int nextTime2) {
        this.nextTime2 = nextTime2;
    }

    public double getNextDensity2() {
        return nextDensity2;
    }

    public void setNextDensity2(double nextDensity2) {
        this.nextDensity2 = nextDensity2;
    }
}



