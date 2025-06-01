package com.harshit.journalApp.utils;

public class ErrorResponse {
    private String status;
    private String error;

    public ErrorResponse(String status, String error){
        this.status = status;
        this.error = error;
    }

    public String getStatus(){
        return status;
    }

    public String getError(){
        return error;
    }
}
