package urjc.ugc.ultragamecenter.api_models;

import java.sql.Date;
import java.util.ArrayList;

import urjc.ugc.ultragamecenter.models.Event;

public class API_event {
    private String name="No existe este evento";
    private String description="El evento no existe";
    private Date date=null;
    private Integer likes=0;
    private ArrayList<String> lavels=null;
    private Integer capacity=0;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Integer getLikes() {
        return likes;
    }

    public ArrayList<String> getLavels() {
        return lavels;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public API_event(Event e) {
        if (e != null) {
            this.name = e.getName();
            this.description = e.getDescription();
            this.date = e.getDate();
            this.likes = e.getlikes();
            this.lavels = e.getLavels();
            this.capacity = e.getCapacity();
        } 
    }

}
