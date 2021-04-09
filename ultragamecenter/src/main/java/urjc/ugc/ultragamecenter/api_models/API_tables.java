package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.Tablegame;

public class API_tables {
    private Long id;
    private String type;

    public API_tables(Tablegame t){
        id=t.getId();
        type=t.getType();
    }

    public static ArrayList<API_tables> transform(List<Tablegame> e){
        ArrayList<API_tables> a=new ArrayList<>();
        for(Tablegame er:e){
            a.add(new API_tables(er));
        }
        return a;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
