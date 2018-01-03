package com.springboot_camel.base.model;

import java.util.Date;

public class VisitHistory {
    private Date visitedDate;
    private long memberId;
    private String ExerciseZone;

    public Date getVisitedDate() {
        return visitedDate;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getExerciseZone() {
        return ExerciseZone;
    }

    public void setExerciseZone(String exerciseZone) {
        ExerciseZone = exerciseZone;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void setVisitedDate(Date visitedDate) {
        this.visitedDate = visitedDate;
    }
}
