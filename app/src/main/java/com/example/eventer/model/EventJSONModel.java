package com.example.eventer.model;

import com.google.gson.annotations.SerializedName;

public class EventJSONModel {

    @SerializedName("eventId")
    private Integer eventId;
    @SerializedName("eventName")
    private String eventName;
    @SerializedName("dateOfEvent")
    private String dateOfEvent;
    @SerializedName("description")
    private String description;
    @SerializedName("imgURL")
    private String imgURL;
    @SerializedName("eventQRCode")
    private String eventQRCode;
    @SerializedName("invitedGuests")
    private String invitedGuests;

    public EventJSONModel(Integer eventId, String eventName, String dateOfEvent, String description, String imgURL, String eventQRCode, String invitedGuests) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.description = description;
        this.imgURL = imgURL;
        this.eventQRCode = eventQRCode;
        this.invitedGuests = invitedGuests;
    }
}
