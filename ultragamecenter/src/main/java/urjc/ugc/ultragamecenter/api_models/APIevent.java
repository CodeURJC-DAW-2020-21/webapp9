package urjc.ugc.ultragamecenter.api_models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.Models.Event;

public class APIevent {
    private String name="No existe este evento";
    private String description="";
    private Date date=null;
    private Integer likes=0;
    private ArrayList<String> lavels=null;
    private Integer capacity=0;

    public void event(String name){
        this.name = "No estás logeado";
    }

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

    public List<String> getLavels() {
        return lavels;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public APIevent(Event e) {
        if (e != null) {
            this.name = e.getName();
            this.description = e.getDescription();
            this.date = e.getDate();
            this.likes = e.getlikes();
            this.lavels = (ArrayList<String>) e.getLavels();
            this.capacity = e.getCapacity();
        }
    }

    public APIevent(String e){
        name = e;
    }

}
