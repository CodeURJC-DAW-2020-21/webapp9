package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;

import urjc.ugc.ultragamecenter.models.Tablegame;

public class API_table {

    private String type = "No existe esta mesa";
    private ArrayList<Integer> state = null;

    public API_table(Tablegame t, Boolean b) {
        if (t != null) {
            if (b) {
                type = t.getType();
                state = t.getState();
            } else {
                type = "No eres administrador";
                state = null;
            }
        }
    }

    public String getType() {
        return type;
    }

    public ArrayList<Integer> getState() {
        return state;
    }
}
