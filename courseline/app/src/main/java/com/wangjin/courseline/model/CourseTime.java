package com.wangjin.courseline.model;

/**
 * Created by wangjin on 16/5/30.
 */
public class CourseTime {

    private int beginTime;

    private int endTime;

    private int week;

    private String place;

    public int getBeginTime() {
        return beginTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
