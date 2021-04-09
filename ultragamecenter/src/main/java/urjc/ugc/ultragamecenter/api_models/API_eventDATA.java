package urjc.ugc.ultragamecenter.api_models;

import urjc.ugc.ultragamecenter.models.Event;

public class API_eventDATA {
    private String name = "No existe este evento";
    private Double ocupation = 0.00;

    public API_eventDATA(Event e) {
        if (e != null) {
            name = e.getName();
            ocupation = e.getlikes() / e.getCapacity() + 0.00;
        }
    }

    public String getName() {
        return name;
    }

    public Double getOcupation() {
        return ocupation;
    }
}
