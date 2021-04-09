package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;

import urjc.ugc.ultragamecenter.models.Tablegame;

public class API_table {
    private String type;
    private ArrayList<Integer> state;

    public API_table(Tablegame t){
        type = t.getType();
        state = t.getState();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Integer> getState() {
        return state;
    }

    public void setState(ArrayList<Integer> state) {
        this.state = state;
    }
}
