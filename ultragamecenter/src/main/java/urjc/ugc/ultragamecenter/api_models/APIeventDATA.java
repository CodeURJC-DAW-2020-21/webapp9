package urjc.ugc.ultragamecenter.api_models;

import urjc.ugc.ultragamecenter.models.Event;

public class APIeventDATA {
    private String name = "No existe este evento";
    private Integer ocupation = 0;
    private Integer total=0;


    public APIeventDATA(Event e) {
        if (e != null) {
            name = e.getName();
            ocupation = e.getlikes();
            total=e.getCapacity() ;
        }
    }

    public APIeventDATA(String e) {
        name = e;
    }

    public String getName() {
        return name;
    }

    public Integer getOcupation() {
        return ocupation;
    }

    public Integer getCapacity() {
        return total;
    }
}
