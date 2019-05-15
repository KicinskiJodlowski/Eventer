package com.example.eventer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventModel implements Serializable {

    @SerializedName("eventId")
    @Expose
    private Integer eventId;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("dateOfEvent")
    @Expose
    private String dateOfEvent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imgURL")
    @Expose
    private Object imgURL;
    @SerializedName("eventQRCode")
    @Expose
    private String eventQRCode;
    @SerializedName("jobIDscheduler")
    @Expose
    private String jobIDscheduler;
    @SerializedName("ownerID")
    @Expose
    private String ownerID;
    @SerializedName("invitedGuests")
    @Expose
    private List<InvitedGuest> invitedGuests = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public EventModel() {
    }

    /**
     *
     * @param ownerID
     * @param eventId
     * @param invitedGuests
     * @param description
     * @param eventQRCode
     * @param imgURL
     * @param jobIDscheduler
     * @param dateOfEvent
     * @param eventName
     */
    public EventModel(Integer eventId, String eventName, String dateOfEvent, String description, Object imgURL, String eventQRCode, String jobIDscheduler, String ownerID, List<InvitedGuest> invitedGuests) {
        super();
        this.eventId = eventId;
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.description = description;
        this.imgURL = imgURL;
        this.eventQRCode = eventQRCode;
        this.jobIDscheduler = jobIDscheduler;
        this.ownerID = ownerID;
        this.invitedGuests = invitedGuests;
    }

    public EventModel(String eventName, String dateOfEvent, String description) {
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.description = description;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImgURL() {
        return imgURL;
    }

    public void setImgURL(Object imgURL) {
        this.imgURL = imgURL;
    }

    public String getEventQRCode() {
        return eventQRCode;
    }

    public void setEventQRCode(String eventQRCode) {
        this.eventQRCode = eventQRCode;
    }

    public String getJobIDscheduler() {
        return jobIDscheduler;
    }

    public void setJobIDscheduler(String jobIDscheduler) {
        this.jobIDscheduler = jobIDscheduler;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public List<InvitedGuest> getInvitedGuests() {
        return invitedGuests;
    }

    public void setInvitedGuests(List<InvitedGuest> invitedGuests) {
        this.invitedGuests = invitedGuests;
    }

}
