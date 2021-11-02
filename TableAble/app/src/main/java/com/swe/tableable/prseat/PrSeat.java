package com.swe.tableable.prseat;

public class PrSeat {
    private String placeName;
    private String pr_time;
    private int pr_seat;

    public PrSeat(String placeName, String pr_time, int pr_seat) {
        this.placeName = placeName;
        this.pr_time = pr_time;
        this.pr_seat = pr_seat;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPr_time() {
        return pr_time;
    }

    public void setPr_time(String pr_time) {
        this.pr_time = pr_time;
    }

    public int getPr_seat() {
        return pr_seat;
    }

    public void setPr_seat(int pr_seat) {
        this.pr_seat = pr_seat;
    }
}
