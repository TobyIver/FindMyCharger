package com.app.toby.findmycharger;

/**
 * Created by Toby on 11/11/2014.
 */
public class Data {
    private long mId;
    private String mlong;
    private String mlat;
    private String mDate;

    private static final String SQL_ID = "id";
    private static final String SQL_LONG_ = "long";
    private static final String SQL_LAT_ = "lat";
    private static final String SQL_DATE = "date";

    //Getting and Setting info for data
    public String getlong() {
        return mlong;
    }
    public void setLong(String loc) {
        this.mlong = loc;
    }
    public String getlat() {
        return mlat;
    }
    public void setLat(String loc) {
        this.mlat = loc;
    }
    public long getId() {
        return mId;
    }
    public void setId(long id) {
        this.mId = id;
    }
    //date getters and setters
    public String getDate() {
        return mDate;
    }
    public void setDate(String date) {
        mDate = date;
    }




}


