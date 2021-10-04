package com.example.myapplication;

public class MessageModel {
    private String from_contact;
    private String to_contact;
    private String message;

    public MessageModel(){}

    public MessageModel(String from_contact, String to_contact, String message) {
        this.from_contact = from_contact;
        this.to_contact = to_contact;
        this.message = message;
    }

    public String getFrom_contact() {
        return from_contact;
    }

    public void setFrom_contact(String from_contact) {
        this.from_contact = from_contact;
    }

    public String getTo_contact() {
        return to_contact;
    }

    public void setTo_contact(String to_contact) {
        this.to_contact = to_contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
