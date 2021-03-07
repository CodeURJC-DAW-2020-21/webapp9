package urjc.ugc.ultragamecenter.Models;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import urjc.ugc.ultragamecenter.Types.EventLavelType;

@Entity
@Table(name="Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer likes;
    private ArrayList<EventLavelType> lavels;
    //private ArrayList<User> assistants;
    private Integer capacity;

    public Event(){}
    public Event(String name, String description, EventLavelType... lavels){
        
        this.name=name;
        this.description=description;
        this.likes=0;
        for (EventLavelType var : lavels) {
            this.lavels.add(var);
        }
    }

    public ArrayList<EventLavelType> getLavels(){
        return lavels;
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
