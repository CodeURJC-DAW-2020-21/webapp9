package urjc.ugc.ultragamecenter.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import urjc.ugc.ultragamecenter.types.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String passwordHash;
    private String lastName;
    private String profileSrc;
    private String email;
    private ArrayList<Long> eventsLikeIt;
    private ArrayList<String> referencedCodes;
    private HashMap<String, Double> affinity;
    private ArrayList<Event> recomendated;
    private RoleType rol;

    public User() {
    }

     
    private Double getAffinity(Event e) {
        Double aux = 0.0;
        for (String lavel : e.getLavels()) {
            if (affinity.containsKey(lavel)) {
                aux += affinity.get(lavel);
            } else {
                affinity.put(lavel, 0.5);
            }
        }
        return aux;
    }

    public void refresh(Event e) {
        Double aux = getAffinity(e);
        Double comparator = getAffinity(recomendated.get(recomendated.size() - 1));
        if (comparator < aux) {
            recomendated.remove(recomendated.size() - 1);
            recomendated.add(e);
            recomendated.sort((e1, e2) -> (int) (100 * (getAffinity(e1) - getAffinity(e2))));
        }
    }

    public User(String name, String lastname, String password, String email) {
        this.name = name;
        this.lastName = lastname;
        this.profileSrc = "images/uploads/defaultuser.png";
        this.passwordHash = password;
        this.email = email;
        this.eventsLikeIt = new ArrayList<Long>();
        this.referencedCodes = new ArrayList<String>();
        this.rol = RoleType.REGISTERED_USER;
    }

    public User(String name, String password, String email, RoleType role) {
        this.name = name;
        this.passwordHash = password;
        this.lastName = "";
        this.email = email;
        this.eventsLikeIt = new ArrayList<Long>();
        this.referencedCodes = new ArrayList<String>();
        this.rol = role;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.passwordHash;
    }

    public ArrayList<Long> getEventsLiked() {
        return this.eventsLikeIt;
    }

    public void setEventLiked(Event event) {
        this.eventsLikeIt.add(event.getId());
    }

    public ArrayList<String> getReferencedCodes() {
        return this.referencedCodes;
    }

    public RoleType getRoles() {
        return rol;
    }

    public void setRoles(RoleType roles) {
        this.rol = roles;
    }

    public void setReferencedCode(ArrayList<String> rc) {
        this.referencedCodes=rc;
    }

    public String getProfileSrc(){
        return profileSrc;
    }

    public void setProfileSrc(String src){
        this.profileSrc = src;
    }

    @Override
    public String toString() {
        return "User [id=" + this.id + ", Name=" + this.name + ", lastName=" + this.lastName + ", email=" + this.email
                + ", envets liked=" + this.eventsLikeIt + ", password=" + this.passwordHash + ", referencedCodes="
                + this.referencedCodes + ", rol=" + this.rol + "]";
    }

    public void likedEvent(Event e,List<Event> allEvents) {
        ArrayList<String> aux=new ArrayList<String>();
        for(Event er: allEvents){
            for(String label:er.getLavels()){
                aux.add(label);
            }
        }
        Double base = 0.5;
        e.like();
        System.out.println(this.affinity);
        this.eventsLikeIt.add(e.getId());
        System.out.println(this.affinity);
        for (String x : aux) {
            System.out.println(this.affinity);
            if (e.getLavels().contains(x)) {
                System.out.println(this.affinity);
                if (this.affinity.containsKey(x)) {
                    System.out.println("Como le ha gustado la etiqueta"+x+" suben sus puntos");
                    this.affinity.put(x, Math.sqrt(this.affinity.get(x)));
                    System.out.println(this.affinity);
                } else {
                    System.out.println("Como le ha gustado la etiqueta "+ x+ " suben sus puntos");
                    this.affinity.put(x, Math.sqrt(base));
                    System.out.println(this.affinity);
                }
            } else {
                if (this.affinity == null) {
                    System.out.println("No había afinidad previa");
                    this.affinity = new HashMap<String,Double>();
                    System.out.println(this.affinity);
                }
                System.out.println("Como no le ha gustado la etiqueta "+x+", bajan sus puntos");
                this.affinity.put(x, base * base);
                System.out.println(this.affinity);
            }
        }
    }

    public void setPassword(String new_password) {
        this.passwordHash = new_password;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public double getValue(Event event) {
        Double aux=0.0;
        if (this.affinity == null) {
            this.affinity = new HashMap<String,Double>();
        }
        for(String label:event.getLavels()){
            if(this.affinity.containsKey(label)){
                aux+=this.affinity.get(label);
            } else{
                aux+=0.5;
                this.affinity.put(label, 0.5);
            }
        }
        return aux;
    }

}
