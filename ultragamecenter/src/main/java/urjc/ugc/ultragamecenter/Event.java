package urjc.ugc.ultragamecenter;

//import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer likes;
    //private ArrayList<User> assistants;
    private Integer capacity;

    public Event(){}
    public Event(String name, String description, Integer likes){
        
        this.name=name;
        this.description=description;
        this.likes=likes;
    }
    
    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public Integer getlikes(){
        return this.likes;
    }
    public Integer getCapacity(){
        return this.capacity;
    }
    

    @Override
	public String toString() {
		return "Event [id=" + id + ", Name=" + name + ", Description=" + description + ", likes=" + likes+"]";
	}

}
