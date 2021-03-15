package urjc.ugc.ultragamecenter.Models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import urjc.ugc.ultragamecenter.Types.EventLavelType;
import urjc.ugc.ultragamecenter.Types.RoleType;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String passwordHash;
    private String lastName;
    private String email;
    private String address;
    private ArrayList<Event> eventsLikeIt;
    private ArrayList<Tablegame> reservatedTables;
    private HashMap<EventLavelType, Double> affinity;
    private ArrayList<Event> recomendated;
    private RoleType rol;

    public User() {
    }

    private Double getAffinity(Event e){
        Double aux=0.0;
        for (EventLavelType lavel : e.getLavels()) {
            if(affinity.containsKey(lavel)){
                aux+=affinity.get(lavel);
            } else{
                affinity.put(lavel, 0.5);
            }
        }
        return aux;
    }

    public void refresh(Event e){
        Double aux= getAffinity(e);
        Double comparator=getAffinity(recomendated.get(recomendated.size()-1));
        if(comparator<aux){
            recomendated.remove(recomendated.size()-1);
            recomendated.add(e);
            recomendated.sort((e1,e2)-> (int)(100*(getAffinity(e1)-getAffinity(e2))));
        }
    }

    public User(String name,  String password, String email) {
        this.name = name;
        this.passwordHash = password;
        this.lastName = "";
        this.email = email;
        this.eventsLikeIt = new ArrayList<Event>();
        this.reservatedTables = new ArrayList<Tablegame>();
        this.rol = RoleType.REGISTERED_USER;
        this.address="";
    }

    public User(String name,  String password, String email, RoleType role) {
        this.name = name;
        this.passwordHash = password;
        this.lastName = "";
        this.email = email;
        this.eventsLikeIt = new ArrayList<Event>();
        this.reservatedTables = new ArrayList<Tablegame>();
        this.rol = role;
        this.address="";
    }

    public Long getId() {
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

    public ArrayList<Event> getEvents() {
        return this.eventsLikeIt;
    }

    public void addEvent(Event event) {
        this.eventsLikeIt.add(event);
    }

    public ArrayList<Tablegame> getTables() {
        return this.reservatedTables;
    }

    public RoleType getRoles() {
        return rol;
    }

    public void setRoles(RoleType roles) {
        this.rol = roles;
    }

    public void addTable(Tablegame table) {
        this.reservatedTables.add(table);
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return this.address;
    }
    @Override
    public String toString() {
        return "User [id=" + this.id + ", Name=" + this.name + ", lastName=" + this.lastName + ", email=" + this.email +", address=" + this.address + ", envets liked="
                + this.eventsLikeIt + ", password=" + this.passwordHash + ", Tables=" + this.reservatedTables + ", rol=" + this.rol
                + "]";
    }

    public void likedEvent(Event e) {
        for (EventLavelType x : EventLavelType.values()) {
            if (e.getLavels().contains(x)) {
                if (this.affinity.containsKey(x)) {
                    this.affinity.put(x, Math.sqrt(this.affinity.get(x)));
                } else {
                    this.affinity.put(x, Math.sqrt(0.5));
                }
            } else {
                this.affinity.put(x, this.affinity.get(x)*this.affinity.get(x));
            }
        }
    }

    public void setPassword(String new_password) {
        this.passwordHash=new_password;
    }

    public void setLastName(String surname) {
        this.lastName=surname;
    }

    public void setName(String name2) {
        this.name=name2;
    }

}
