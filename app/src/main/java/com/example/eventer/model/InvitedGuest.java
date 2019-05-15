package com.example.eventer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvitedGuest {

    @SerializedName("invitedGuestsID")
    @Expose
    private Integer invitedGuestsID;
    @SerializedName("eventId")
    @Expose
    private Integer eventId;
    @SerializedName("userId")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     *
     */
    public InvitedGuest() {
    }

    /**
     *
     * @param eventId
     * @param userId
     * @param invitedGuestsID
     */
    public InvitedGuest(Integer invitedGuestsID, Integer eventId, String userId) {
        super();
        this.invitedGuestsID = invitedGuestsID;
        this.eventId = eventId;
        this.userId = userId;
    }

    public Integer getInvitedGuestsID() {
        return invitedGuestsID;
    }

    public void setInvitedGuestsID(Integer invitedGuestsID) {
        this.invitedGuestsID = invitedGuestsID;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}