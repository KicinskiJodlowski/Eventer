package com.example.eventer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestModel {

    @SerializedName("eventId")
    @Expose
    private Integer eventId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("invitedGuestsID")
    @Expose
    private Integer invitedGuestsID;

    /**
     * No args constructor for use in serialization
     *
     */
    public GuestModel() {
    }

    /**
     *
     * @param eventId
     * @param email
     * @param invitedGuestsID
     * @param userId
     * @param userName
     * @param fullname
     */
    public GuestModel(Integer eventId, String userId, String userName, String email, String fullname, Integer invitedGuestsID) {
        super();
        this.eventId = eventId;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.fullname = fullname;
        this.invitedGuestsID = invitedGuestsID;
    }

    public GuestModel(String fullname) {
        super();
        this.fullname = fullname;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getInvitedGuestsID() {
        return invitedGuestsID;
    }

    public void setInvitedGuestsID(Integer invitedGuestsID) {
        this.invitedGuestsID = invitedGuestsID;
    }

}
