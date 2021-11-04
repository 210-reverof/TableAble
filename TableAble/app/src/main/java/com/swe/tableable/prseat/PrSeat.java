package com.swe.tableable.prseat;

public class PrSeat {
    private String placeName;
    private String prTime;
    private int prSeat;

    public PrSeat(String placeName, String pr_time, int prSeat) {
        this.placeName = placeName;
        this.prTime = pr_time;
        this.prSeat = prSeat;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPrTime() {
        return prTime;
    }

    public void setPrTime(String prTime) {
        this.prTime = prTime;
    }

    public int getPrSeat() {
        return prSeat;
    }

    public void setPrSeat(int prSeat) {
        this.prSeat = prSeat;
    }
}
