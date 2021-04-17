package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.Event;

public class APIevents {
    private String name = "No existe este evento";
    private Long id = 0L;

    public APIevents(Event e) {
        if (e != null) {
            this.name = e.getName();
            this.id = e.getId();
        }
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static List<APIevents> transform(List<Event> e) {
        ArrayList<APIevents> a = new ArrayList<>();
        for (Event er : e) {
            a.add(new APIevents(er));
        }
        return a;
    }
}
