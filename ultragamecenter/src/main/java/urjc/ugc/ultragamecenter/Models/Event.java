package urjc.ugc.ultragamecenter.models;

import java.sql.Date;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public static ArrayList<String> allLabels= new ArrayList<String>();

    private String name;
    private String description;
    private Date date;
    private String bannerUrl;
    private Integer likes;
    private ArrayList<String> lavels;
    private Integer capacity;

    public Event(){}
    

    public Event(String name, String description, String date2, String bannerUrl, Integer capacity) {
        this.date=Date.valueOf(date2);
        this.name=name;
        this.description=description;
        this.likes=0;
        this.bannerUrl = bannerUrl;
        this.lavels = new ArrayList<String>();
        this.capacity = capacity;
    }

    public ArrayList<String> getLavels(){
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
    public void setDate(String date) {
        this.date = Date.valueOf(date);
    }
    public String getBannerUrl() {
        return bannerUrl;
    }
    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
    public void putLavel(String lavel){
        this.lavels.add(lavel);
    }

    public void setCapacity(Integer capacity){
        this.capacity = capacity;
    }

    public void like() {
        this.likes ++;
    }

    public void setDescription(String object) {
        this.description=object;
    }

    public void setName(String object) {
        this.name=object;
    }

}
