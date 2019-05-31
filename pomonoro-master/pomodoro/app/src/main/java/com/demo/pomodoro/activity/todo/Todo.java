package com.demo.pomodoro.activity.todo;

public class Todo {
    private Integer id;
    private String title;
    private String description;
    private Integer count;
    private String date;
    private Integer countRecent;
    private Integer longBreak;
    private Integer monthJob;
    private Integer currentMonth;
    private Integer CountCircle;
    public Todo() {
    }

    public Todo(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Integer getCountCircle() {
        return CountCircle;
    }

    public void setCountCircle(Integer countCircle) {
        CountCircle = countCircle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Todo(Integer count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Todo(String date) {
        this.date = date;
    }

    public Integer getCountRecent() {
        return countRecent;
    }

    public void setCountRecent(Integer countRecent) {
        this.countRecent = countRecent;
    }

    public Integer getLongBreak() {
        return longBreak;
    }

    public void setLongBreak(Integer longBreak) {
        this.longBreak = longBreak;
    }

    public Integer getMonthJob() {
        return monthJob;
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setMonthJob(Integer monthJob) {
        this.monthJob = monthJob;
    }

    public Todo(String title, Integer countCircle) {
        this.title = title;
        CountCircle = countCircle;
    }

    public Todo(Integer id, Integer count, Integer currentMonth) {
        this.id = id;
        this.count = count;
        this.currentMonth = currentMonth;
    }
}
