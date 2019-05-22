package com.example.eventer.model;

import com.example.eventer.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class NotifyRegisterModel {

    private String Platform;
    private String Handle;
    private ArrayList<String> Tags;

    public NotifyRegisterModel(String handle) {
        Platform = "gcm";
        Handle = handle;
        Tags = new ArrayList<>(Arrays.asList(SharedPreferenceManager.read(SharedPreferenceManager.Login,"")));
    }

    public String getPlatform() {
        return Platform;
    }

    public String getHandle() {
        return Handle;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public void setHandle(String handle) {
        Handle = handle;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }
    //    {
//        "Platform":"gcm",
//        "Handle":"di4QGaaWBo0:APA91bHl5DZna0Kx8cfN2xDvNGocvFkoxw0wsl6YU2hT0vHIrT87_gtouxM6sNdBx2E5vFfjQBM68BkkdsdFwzbyvxeHblBrPULwfND_vflfmDjj7JoZJQ-q-tM8GkndIlFjinKfF-5P",
//        "Tags":["Rafal"]
//    }
}
