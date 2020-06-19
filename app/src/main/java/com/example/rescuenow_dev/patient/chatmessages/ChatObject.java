package com.example.rescuenow_dev.patient.chatmessages;

import java.util.Map;

public class ChatObject {

    private String message;
    private Boolean currentUser;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Boolean currentUser) {
        this.currentUser = currentUser;
    }

    public ChatObject(String message, Boolean currentUser,String timeStamp) {
        this.message = message;
        this.currentUser = currentUser;
        this.timeStamp = timeStamp;
    }

    public ChatObject(String message, Boolean currentUser) {
        this.message = message;
        this.currentUser = currentUser;
    }


}
