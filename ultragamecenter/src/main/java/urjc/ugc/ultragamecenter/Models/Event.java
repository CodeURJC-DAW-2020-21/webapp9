package urjc.ugc.ultragamecenter.Models;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Integer capacity;

    public Event(){}
    
    public Event(String name, String description, String date2, String bannerUrl, Integer capacity) throws ParseException{
        this.date=Date.valueOf(date2);
        this.name=name;
        this.description=description;
        this.likes=0;
        this.bannerUrl = bannerUrl;
        this.lavels = new ArrayList<EventLavelType>();
        this.capacity = capacity;
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
    public void putLavel(EventLavelType lavel){
        this.lavels.add(lavel);
    }

    public void setCapacity(Integer capacity){
        this.capacity = capacity;
    }

    public void like() {
        this.likes ++;
    }

}
