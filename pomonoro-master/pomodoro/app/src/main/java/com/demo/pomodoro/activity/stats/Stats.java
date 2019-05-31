package com.demo.pomodoro.activity.stats;

public class Stats {
    private Integer id;
    private String date;
    private Integer countJob;
    private Integer monthJob;

    public Stats() {
    }

    public Stats(Integer id, String date, Integer countJob, Integer monthJob) {
        this.id = id;
        this.date = date;
        this.countJob = countJob;
        this.monthJob = monthJob;
    }

    public Stats(Integer monthJob) {
        this.monthJob = monthJob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCountJob() {
        return countJob;
    }

    public void setCountJob(Integer countJob) {
        this.countJob = countJob;
    }

    public Integer getMonthJob() {
        return monthJob;
    }

    public void setMonthJob(Integer monthJob) {
        this.monthJob = monthJob;
    }
}
