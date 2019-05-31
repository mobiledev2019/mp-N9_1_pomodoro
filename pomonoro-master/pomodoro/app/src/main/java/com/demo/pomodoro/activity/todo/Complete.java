package com.demo.pomodoro.activity.todo;

public class Complete {
    private Integer Id;
    private String monthCurrent;

    public Complete() {
    }

    public Complete(Integer id, String monthCurrent) {
        Id = id;
        this.monthCurrent = monthCurrent;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getMonthCurrent() {
        return monthCurrent;
    }

    public void setMonthCurrent(String monthCurrent) {
        this.monthCurrent = monthCurrent;
    }
}
