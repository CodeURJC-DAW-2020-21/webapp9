package urjc.ugc.ultragamecenter.api_models;

import java.sql.Date;
import java.util.ArrayList;

import urjc.ugc.ultragamecenter.models.Event;

public class API_event {
    private String name;
    private String description;
    private Date date;
    private Integer likes;
    private ArrayList<String> lavels;
    private Integer capacity;

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
        this.name = e.getName();
        this.description = e.getDescription();
        this.date = e.getDate();
        this.likes = e.getlikes();
        this.lavels = e.getLavels();
        this.capacity = e.getCapacity();
    }

}
