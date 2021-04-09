package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.Tablegame;

public class API_tables {

    private Long id = 0L;
    private String type = "NO EXISTE ESTA MESA";

    public API_tables(Tablegame t, Boolean b) {
        if (t != null) {
            if (b) {
                id = t.getId();
                type = t.getType();
            } else {
                id = 0l;
                type = "NO ERES ADMINISTRADOR";
            }
        }
    }

    public static ArrayList<API_tables> transform(List<Tablegame> e, Boolean b) {
        ArrayList<API_tables> a = new ArrayList<>();
        if (b) {
            for (Tablegame er : e) {
                a.add(new API_tables(er,b));
            }
        } else{
            a.add(new API_tables(new Tablegame(),b));
        }
        return a;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
