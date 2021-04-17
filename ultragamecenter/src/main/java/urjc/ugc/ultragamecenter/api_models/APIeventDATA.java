package urjc.ugc.ultragamecenter.api_models;

import urjc.ugc.ultragamecenter.Models.Event;

public class APIeventDATA {
    private String name = "No existe este evento";
    private Double ocupation = 0.00;

    public APIeventDATA(Event e) {
        if (e != null) {
            name = e.getName();
            ocupation = e.getlikes() / e.getCapacity() + 0.00;
        }
    }

    public APIeventDATA(String e) {
        name = e;
    }

    public String getName() {
        return name;
    }

    public Double getOcupation() {
        return ocupation;
    }
}
