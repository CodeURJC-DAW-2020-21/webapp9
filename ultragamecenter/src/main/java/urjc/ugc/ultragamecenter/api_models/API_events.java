package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.Event;

public class API_events {
    private String name;
    private Long id;

    public API_events(Event e){
        this.name=e.getName();
        this.id=e.getId();
    }

    public String getName(){
        return name;
    }
    public Long getId(){
        return id;
    }

    public static ArrayList<API_events> transform(List<Event> e){
        ArrayList<API_events> a=new ArrayList<>();
        for(Event er:e){
            a.add(new API_events(er));
        }
        return a;
    }
}
