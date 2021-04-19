package urjc.ugc.ultragamecenter.models;

import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


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
    private ArrayList<String> lavels;
    private ArrayList<String> gallery;
    private Integer capacity;
    @Lob
	private Blob imageFile;
    public Event(){}


    public Event(String name, String description, String date2, String bannerUrl, Integer capacity) {
        this.date=Date.valueOf(date2);
        this.name=name;
        this.description=description;
        this.likes=0;
        this.bannerUrl = bannerUrl;
        this.lavels = new ArrayList<>();
        this.gallery = new ArrayList<>();
        this.capacity = capacity;
    }

    public List<String> getLavels(){
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


    public  List<String> getGallery(){
        return gallery;
    }

    public void setGallery(String... gallery) {
        for(String g: gallery){
            this.gallery.add(g);
        }
    }

    @Override
	public String toString() {
		return "Event [id=" + id + ", Name=" + name + ", Description=" + description + ", likes=" + likes+", Labels="+this.lavels+"]";
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

    public Blob getImageFile() {
		return imageFile;
	}

	public void setImageFile(Blob image) {
		this.imageFile = image;
	}

}
