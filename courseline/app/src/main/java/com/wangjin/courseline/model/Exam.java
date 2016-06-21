package com.wangjin.courseline.model;

/**
 * Created by zhengsuren on 16/6/19.
 */
public class Exam {

    private int id;

    private String start_time,location,subject,end_time,date,remark,start_end;

    public String getStart_time() {
        return start_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {

        return date;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_end()
    {
        start_end = start_time + "-" + end_time;

        if (start_end.equals("-"))
        {
            return "";
        }

        return start_end;
    }
}
