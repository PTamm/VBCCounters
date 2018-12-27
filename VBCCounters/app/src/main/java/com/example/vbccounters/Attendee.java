package com.example.vbccounters;

public class Attendee {

    private String attendeeName;
    private int count;
    private boolean isMember;

    Attendee(String attendeeName, String attendeeType){
        this.attendeeName = attendeeName;
        this.count = 0;
        this.isMember = attendeeType.equals("Member");
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

    public boolean checkIsMember(){
        return this.isMember;
    }

}
