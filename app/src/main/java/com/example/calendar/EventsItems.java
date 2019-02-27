package com.example.calendar;

public class EventsItems {
    public String id;
    public String title;
    public String location;
    public String starttime;
    public String endtime;


    public EventsItems(String id, String title, String location, String starttime, String endtime) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.starttime = starttime;
        this.endtime = endtime;
    }
}
