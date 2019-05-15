package com.example.eventer.custom;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class CustomEvent implements Serializable {

    int eventID;
    String eventTitle;
    String eventStartDate;
    String eventDesc;
    int eventFounderID;

    public CustomEvent(String eventTitle, String eventStartDate, String eventDesc) {
        this.eventTitle = eventTitle;
        this.eventStartDate = eventStartDate;
        this.eventDesc = eventDesc;
    }

    public CustomEvent(int eventID, String eventTitle, String eventStartDate, String eventDesc, int eventFounderID) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventStartDate = eventStartDate;
        this.eventDesc = eventDesc;
        this.eventFounderID = eventFounderID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public int getEventFounderID() {
        return eventFounderID;
    }

    public void setEventFounderID(int eventFounderID) {
        this.eventFounderID = eventFounderID;
    }
}
