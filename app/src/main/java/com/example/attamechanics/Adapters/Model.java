package com.example.attamechanics.Adapters;

public class Model {
    private String task, description, id, price;

    public Model(){

    }

    public Model(String task, String description, String id, String price) {
        this.task = task;
        this.description = description;
        this.id = id;
        this.price = price;
    }



    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

}
