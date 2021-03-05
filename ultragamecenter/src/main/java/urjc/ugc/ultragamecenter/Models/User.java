package urjc.ugc.ultragamecenter.Models;

import java.util.ArrayList;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;




@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String passwordHash;
    private String lastName;
    private String email;
    private ArrayList<Event> eventsLikeIt;
    private ArrayList<Tablegame> reservatedTables;

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

    public User(){}
    
    public User(String name, String passwordHash, String lastName, String email, String... roles) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.lastName = lastName;
        this.email = email;
        this.eventsLikeIt = new ArrayList<Event>();
        this.reservatedTables = new ArrayList<Tablegame>();
        this.roles = List.of(roles);
    }

    
    public Long getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.passwordHash;
    }

    public ArrayList<Event> getEvents() {
		return this.eventsLikeIt;
	}

    public void addEvent(Event event){
        this.eventsLikeIt.add(event);
    }

    public ArrayList<Tablegame> getTables() {
		return this.reservatedTables;
	}

    public List<String> getRoles() {
		return roles;
	}

    public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public void addTable(Tablegame table){
        this.reservatedTables.add(table);
    }

    @Override
	public String toString() {
		return "User [id=" + id + ", Name=" + name + ", lastName=" + lastName + ", email=" + email
				+ ", envets liked=" + eventsLikeIt + ", password=" + passwordHash + ", Tables=" + reservatedTables + ", roles=" + roles
				+"]";
	}



}
