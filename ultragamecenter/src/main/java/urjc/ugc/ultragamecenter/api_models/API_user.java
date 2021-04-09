package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;


import urjc.ugc.ultragamecenter.models.User;

public class API_user {

    private String name="No";
    private String lastName="Estas";
    private String email="Logeado";
    private ArrayList<Long> eventsLikeIt=null;
    private ArrayList<String> referencedCodes=null;
    private List<String> roles=null;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Long> getEventsLikeIt() {
        return eventsLikeIt;
    }

    public ArrayList<String> getReferencedCodes() {
        return referencedCodes;
    }

    public List<String> getRoles() {
        return roles;
    }

    public API_user(User u) {
        if (u != null) {
            this.name = u.getName();
            this.lastName = u.getLastName();
            this.email = u.getEmail();
            this.eventsLikeIt = u.getEventsLiked();
            this.referencedCodes = u.getReferencedCodes();
            this.roles = u.getRoles();
        } 

    }

}
