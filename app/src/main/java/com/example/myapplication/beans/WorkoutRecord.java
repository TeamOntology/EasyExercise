package com.example.myapplication.beans;

import java.io.Serializable;
import java.util.Date;

public class WorkoutRecord extends WorkoutPlan implements Serializable {
    private Date startTime;
    private Date endTime;

//    public WorkoutRecord(){
//        super();
//    }

    public WorkoutRecord(Sport sport, Location location, long id, Date startTime, Date endTime) {
        super(sport, location, id);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
