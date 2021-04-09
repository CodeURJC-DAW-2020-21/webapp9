package urjc.ugc.ultragamecenter.api_models;

import urjc.ugc.ultragamecenter.models.Event;

public class API_eventDATA {
    private String name;
    private Double ocupation;

    public API_eventDATA(Event e){
        name=e.getName();
        ocupation=e.getlikes()/e.getCapacity() + 0.00;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOcupation() {
        return ocupation;
    }

    public void setOcupation(Double ocupation) {
        this.ocupation = ocupation;
    }
}
