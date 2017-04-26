package com.myapp.universitywidget;

public class Event {

    private int type;
    private String auditory;
    private String brief;

    Event(int type, String auditory, String brief) {
        this.type = type;
        this.auditory = auditory;
        this.brief = brief;
    }

    public int getType() {
        return type;
    }

    public String getAuditory() {
        return auditory;
    }

    public String getBrief() {
        return brief;
    }
}
