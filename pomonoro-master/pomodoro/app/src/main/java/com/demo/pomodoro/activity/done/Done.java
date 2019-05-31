package com.demo.pomodoro.activity.done;

public class Done {
    private Integer id;
    private String title,description;

    public Done() {
    }

    public Done(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Done(String title, String description) {
        this.title = title;
        this.description = description;
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
}
