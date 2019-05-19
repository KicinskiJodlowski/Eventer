package com.example.eventer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RegisterResponseModel {


    private Boolean succeeded;
    private List<Error> errors;

    public RegisterResponseModel(Boolean succeeded, List<Error> errors) {
        this.succeeded = succeeded;
        this.errors = errors;
    }

    public Boolean getSucceeded() {
        return succeeded;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setSucceeded(Boolean succeeded) {
        this.succeeded = succeeded;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
    //    "succeeded":true,
//    "errors":[]

//    "succeeded":false,
//            "errors":[
//
//    {
//        "code":"DuplicateUserName",
//            "description":"User name 'Kamil' is already taken."
//    },
//    {
//        "code":"PasswordTooShort",
//        "description":"Passwords must be at least 6 characters."
//    },
//
//    {
//        "code":"PasswordRequiresLower",
//            "description":"Passwords must have at least one lowercase ('a'-'z')."
//    },
//
//    {
//        "code":"PasswordRequiresUpper",
//            "description":"Passwords must have at least one uppercase ('A'-'Z')."
//    }
//    ]
}
