package urjc.ugc.ultragamecenter.Models;

import java.sql.Date;
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
    private Date date;
    private String bannerUrl;
    private Integer likes;
    private ArrayList<EventLavelType> lavels;
    //private ArrayList<User> assistants;
    private Integer capacity;

    public Event(){}
    public Event(String name, String description, EventLavelType... lavels, Date date, String bannerUrl){
        
        this.name=name;
        this.description=description;
        this.likes=0;
        for (EventLavelType var : lavels) {
            this.lavels.add(var);
        }
        this.date = date;
        this.bannerUrl = bannerUrl;
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
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getBannerUrl() {
        return bannerUrl;
    }
    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

}
