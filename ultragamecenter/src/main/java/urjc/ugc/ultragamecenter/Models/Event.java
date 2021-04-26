package urjc.ugc.ultragamecenter.models;

import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import urjc.ugc.ultragamecenter.requests.EventDTO;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Date date;

    @JsonIgnore
    private String bannerUrl;
    private Integer likes;

    @JsonIgnore
    private ArrayList<String> labels;

    @JsonIgnore
    private ArrayList<String> gallery;

    private Integer capacity;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    public Event() {
    }

    public Event(EventDTO newEvent){
        this.date = newEvent.getDate();
        this.name = newEvent.getName();
        this.description = newEvent.getDescription();
        this.likes = 0;
        this.bannerUrl = "";
        this.labels = newEvent.getLavels();
        this.gallery = new ArrayList<>();
        this.capacity = newEvent.getCapacity();
    }

    public void edit(EventDTO newEvent){
        this.date = newEvent.getDate();
        this.name = newEvent.getName();
        this.description = newEvent.getDescription();
        this.labels = newEvent.getLavels();
        this.capacity = newEvent.getCapacity();
    }

    public Event(String name, String description, String date2, String bannerUrl, Integer capacity) {
        this.date = Date.valueOf(date2);
        this.name = name;
        this.description = description;
        this.likes = 0;
        this.bannerUrl = bannerUrl;
        this.labels = new ArrayList<>();
        this.gallery = new ArrayList<>();
        this.capacity = capacity;
    }

    public List<String> getLabels() {
        return labels;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getlikes() {
        return this.likes;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(String... gallery) {
        for (String g : gallery) {
            this.gallery.add(g);
        }
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", Name=" + name + ", Description=" + description + ", likes=" + likes + ", Labels="
                + this.labels + "]";
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

    public void putLabel(String label) {
        this.labels.add(label);
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void like() {
        this.likes++;
    }

    public void setDescription(String object) {
        this.description = object;
    }

    public void setName(String object) {
        this.name = object;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob image) {
        this.imageFile = image;
    }

    public void setID(Long id2) {
        this.id = id2;
    }

    @JsonIgnore
    public Map<String, String> getDATA() {
        HashMap<String, String> h = new HashMap<>();
        h.put("Aforo total", capacity.toString());
        Integer aux = capacity - likes;
        h.put("Aforo disponible", aux.toString());
        h.put("Aforo restante", likes.toString());
        return h;
    }

}
