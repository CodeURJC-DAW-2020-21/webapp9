package urjc.ugc.ultragamecenter;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String passwordHash;
    private String lastName;
    private String email;
    private ArrayList<Event> eventsLikeIt;
    private ArrayList<Table> reservatedTables;
    private String rol;
    private final static String possibleRoles[]={"Admin","User"};

    public User(String name, String passwordHash, String lastName, String email, String rol) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.lastName = lastName;
        this.email = email;
        this.eventsLikeIt = new ArrayList<Event>();
        this.reservatedTables = new ArrayList<Table>();
        this.rol = rol;
    }

    public static String[] getRoles(){
        return possibleRoles;
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

    public ArrayList<Table> getTables() {
		return this.reservatedTables;
	}

    public void addTable(Table table){
        this.reservatedTables.add(table);
    }

    @Override
	public String toString() {
		return "User [id=" + id + ", Name=" + name + ", lastName=" + lastName + ", email=" + email
				+ ", envets liked=" + eventsLikeIt + ", password=" + passwordHash + ", Tables=" + reservatedTables + ", roles=" + rol
				+"]";
	}



}
