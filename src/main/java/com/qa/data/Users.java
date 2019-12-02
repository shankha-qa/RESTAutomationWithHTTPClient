package com.qa.data;

//POJO - Plain Old Java Object
public class Users {

    String name;
    String job;

    public Users() {
    }

    public Users(final String name, final String job) {
        this.name = name;
        this.job =job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public void setName() {
        this.name = name;
    }

    public void setJob() {
        this.job = job;
    }

}
