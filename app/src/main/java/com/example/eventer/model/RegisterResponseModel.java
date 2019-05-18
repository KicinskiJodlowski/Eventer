package com.example.eventer.model;

public class RegisterResponseModel {

    private String succeeded;
    private String errorrs;

    public RegisterResponseModel(String succeeded, String errorrs) {
        this.succeeded = succeeded;
        this.errorrs = errorrs;
    }

    public String getSucceeded() {
        return succeeded;
    }

    public String getErrorrs() {
        return errorrs;
    }

    public void setSucceeded(String succeeded) {
        this.succeeded = succeeded;
    }

    public void setErrorrs(String errorrs) {
        this.errorrs = errorrs;
    }
    //    "succeeded":true,
//    "errors":[]

//    "succeeded":false,
//            "errors":[
//
//    {
//        "code":"DuplicateUserName",
//            "description":"User name 'Kamil' is already taken."
//    }
//    ]
}
