package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.Models.User;

public class APIuser {

    private String name = "No estas logeado";
    private String lastName = "";
    private String email = "";
    private ArrayList<Long> eventsLikeIt = null;
    private ArrayList<String> referencedCodes = null;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Long> getEventsLikeIt() {
        return eventsLikeIt;
    }

    public List<String> getReferencedCodes() {
        return referencedCodes;
    }



    public APIuser(User u) {
        if (u != null) {
            this.name = u.getName();
            this.lastName = u.getLastName();
            this.email = u.getEmail();
            this.eventsLikeIt = (ArrayList<Long>) u.getEventsLiked();
            this.referencedCodes = (ArrayList<String>) u.getReferencedCodes();
        }

    }

    public APIuser(String u) {
        name=u;
    }

}
