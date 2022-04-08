package com.unicode.eventuni.Model;

import com.google.gson.annotations.SerializedName;

public class PostPutDelEvent {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    Event mEvent;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Event getEvent() {
        return mEvent;
    }
    public void setEvent(Event Event) {
        mEvent = Event;
    }
}
