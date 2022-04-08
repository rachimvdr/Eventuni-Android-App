package com.unicode.eventuni.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetEvent {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Event> listDataEvent;
    @SerializedName("message")
    String message;
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
    public List<Event> getListDataEvent() {
        return listDataEvent;
    }
    public void setListDataEvent(List<Event> listDataEvent) {
        this.listDataEvent = listDataEvent;
    }
}
