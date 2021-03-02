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
    private final String possibleRoles[]={"Admin","User"};

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

    public ArrayList<Event> getEventos() {
		return this.eventsLikeIt;
	}

    public ArrayList<Table> getTables() {
		return this.reservatedTables;
	}

    @Override
	public String toString() {
		return "User [id=" + id + ", Name=" + name + ", lastName=" + lastName + ", email=" + email
				+ ", envets liked=" + eventsLikeIt + ", password=" + passwordHash + ", Tables=" + reservatedTables + ", roles=" + rol
				+"]";
	}

}
