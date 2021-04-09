package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.User;

public class API_user {
    private String name;
    private String lastName;
    private String email;
    private ArrayList<Long> eventsLikeIt;
    private ArrayList<String> referencedCodes;
    private List<String> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Long> getEventsLikeIt() {
        return eventsLikeIt;
    }

    public void setEventsLikeIt(ArrayList<Long> eventsLikeIt) {
        this.eventsLikeIt = eventsLikeIt;
    }

    public ArrayList<String> getReferencedCodes() {
        return referencedCodes;
    }

    public void setReferencedCodes(ArrayList<String> referencedCodes) {
        this.referencedCodes = referencedCodes;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public API_user(User u) {
        this.name = u.getName();
        this.lastName = u.getLastName();
        this.email = u.getEmail();
        this.eventsLikeIt = u.getEventsLiked();
        this.referencedCodes = u.getReferencedCodes();
        this.roles =  u.getRoles();
    }

}
