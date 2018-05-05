package com.scottejames.downsman.model;

public class Model {
    private int id = 0;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public boolean isPersisted() {
        return id != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        return id == model.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}