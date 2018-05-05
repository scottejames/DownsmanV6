package com.scottejames.downsman.services;

import com.scottejames.downsman.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Service<M extends Model> {
    private int id = 0;

    private List<M> data = new ArrayList<>();
    public Service(){

    }
    public  Service(List<M> data) {
        this.data = data;
    }

    protected int getNextId(){
        return ++id;
    }
    public M getById(int id){
        M result = null;
        for (M item: data){
            if (item.getId() == id){
                result = item;
            }
        }
        if (result == null) {
            System.err.println("Did not find item id " + id);
        }
        return result;
    }
    public void reset(){
        for (Model m : data){
            m.setId(0);
        }
        this.data = new ArrayList<>();
        id = 0;
    }
    public  List<M> getAll(){
        return data;

    }
    public void add(M model){
        model.setId(this.getNextId());
        data.add(model);
    }
    public void remove(M model){
        if (model.getId() == 0){
            System.out.println("Unable to remove model without ID");
            System.exit(-1);
        }
        int index = findIndex(model.getId());
        data.remove(index);

    }
    public  void update(M model){
        if (model.getId() == 0){
            System.out.println("Unable to update model without ID");
            System.exit(-1);
        }
        int index = findIndex(model.getId());
        data.remove(index);
        data.add(model);
    }

    private int findIndex(int id){
        int result = 0;
        for (int i = 0; i < data.size(); i++){
            if (this.data.get(i).getId() == id){
                result = i;
            }
        }
        return result;
    }

    public int size() {
        return data.size();
    }

    public void removeById(int i) {
        int index = findIndex(i);
        data.remove(index);

    }
}
