package com.scottejames.downsman.model;

public class TestModel  extends Model {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestModel(String name) {

        this.name = name;
    }

    private String name = null;

}
