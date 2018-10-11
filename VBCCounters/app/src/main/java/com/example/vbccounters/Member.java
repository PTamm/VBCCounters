package com.example.vbccounters;

public class Member {

    private String memberName;
    private int count;

    Member(){
        this.memberName = "Unknown";
        this.count = 0;
    }

    Member(String memName){
        this.memberName = memName;
        this.count = 0;
    }

    public void setName(String memName){
        this.memberName = memName;
    }

    public String getName(){
        return this.memberName;
    }

    public void setCount(int newCount){
        this.count = newCount;
    }

    public int getCount(){
        return this.count;
    }

}
