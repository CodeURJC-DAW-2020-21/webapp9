package urjc.ugc.ultragamecenter.requests;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.Event;

public class EventSRC {
    private String principal;
    private ArrayList<String> galery;
    public String getPrincipal() {
        return principal;
    }
    public EventSRC(String principal, List<String> galery) {
        this.principal = principal;
        this.galery = (ArrayList<String>) galery;
    }

    public EventSRC(Event e){
        this.principal = e.getBannerUrl();
        this.galery=(ArrayList<String>) e.getGallery();
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    public List<String> getGalery() {
        return galery;
    }
    public void setGalery(List<String> galery) {
        this.galery = (ArrayList<String>) galery;
    }
    
}
