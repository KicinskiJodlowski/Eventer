package com.example.eventer.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eventer.activity.MainActivity;

public class Token {
    //tego mozna uzyc jak sie chce ladniej uzywac tokena
    public static String userTOKEN() {
        return TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        Token.TOKEN = "Bearer " + TOKEN;
    }

    private static String TOKEN;
}
