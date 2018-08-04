package com.scottejames.downsman.services;

import com.scottejames.downsman.model.Model;
import com.scottejames.downsman.model.SessionState;
import com.vaadin.flow.server.VaadinSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class Service<M extends Model> {
    private int id = 0;

    // This means that username should not be used to disect data - UserService is not segregated
    private boolean owned = false;

    private final HashMap<String,List<M>> ownedData = new HashMap<>();

    protected String getUser(){
        String username;
        if (owned ){
            username = SessionState.getInstance().getCurrentUser().getUsername();
        } else {
            username = new String("UNOWNED");
        }
        ownedData.putIfAbsent(username, new ArrayList<>());
        return username;
    }
    public Service(boolean owned){
        this.owned = owned;

    }
    public Service(List<M> data) {
        this.ownedData.put(getUser(), data);
    }

    int getNextId(){
        return ++id;
    }

    public M getById(int id){
        M result = null;
        for (M item: this.ownedData.get(getUser())){
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
        for (String userName: ownedData.keySet())
        for (Model m : ownedData.get(userName)){
            m.setId(0);
        }
        HashMap<String,List<M>> ownedData = new HashMap<>();
        id = 0;
    }
    public  List<M> getAll(){

        return ownedData.get(getUser());

    }

    public List <M> getAllAll(){
        ArrayList <M> results = new ArrayList<>();
        for (String key: ownedData.keySet() ){
            results.addAll(ownedData.get(key));
        }
        return results;

    }
    public void add(M model){
        model.setId(this.getNextId());
        ownedData.get(getUser()).add(model);
    }
    public void remove(M model){
        if (model.getId() == 0){
            System.out.println("Unable to remove model without ID");
            System.exit(-1);
        }
        int index = findIndex(model.getId());
        ownedData.get(getUser()).remove(index);

    }
    public  void update(M model){
        if (model.getId() == 0){
            System.out.println("Unable to update model without ID");
            System.exit(-1);
        }
        int index = findIndex(model.getId());
        ownedData.get(getUser()).remove(index);
        ownedData.get(getUser()).add(model);
    }

    private int findIndex(int id){
        int result = 0;
        for (int i = 0; i < ownedData.get(getUser()).size(); i++){
            if (this.ownedData.get(getUser()).get(i).getId() == id){
                result = i;
            }
        }
        return result;
    }

    public int size() {
        return ownedData.get(getUser()).size();
    }

    public void removeById(int i) {
        int index = findIndex(i);
        ownedData.get(getUser()).remove(index);

    }
}
