package com.example.vbccounters;

public class Attendee {

    private String attendeeName;
    private int count;

    Attendee(String attendeeName){
        this.attendeeName = attendeeName;
        this.count = 0;
    }

    public void setName(String memName){
        this.attendeeName = memName;
    }

    public String getName(){
        return this.attendeeName;
    }

    public String toString(){
        return getName();
    }

    public void setCount(int newCount){
        this.count = newCount;
    }

    public int getCount(){
        return this.count;
    }

}
