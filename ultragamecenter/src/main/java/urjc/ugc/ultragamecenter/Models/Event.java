package urjc.ugc.ultragamecenter.models;

import java.io.IOException;
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

    private Integer likes;

    @JsonIgnore
    private ArrayList<String> labels;


    private Integer capacity;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    public Event() {
    }

    public Event(EventDTO newEvent) throws IOException{
        this.date = newEvent.getDate();
        this.name = newEvent.getName();
        this.description = newEvent.getDescription();
        this.likes = 0;
        this.labels = newEvent.getLavels();
        this.capacity = newEvent.getCapacity();
    }

    public void edit(EventDTO newEvent){
        this.date = newEvent.getDate();
        this.name = newEvent.getName();
        this.description = newEvent.getDescription();
        this.labels = newEvent.getLavels();
        this.capacity = newEvent.getCapacity();
    }

    public Event(String name, String description, String date2, Integer capacity) throws IOException {
        this.date = Date.valueOf(date2);
        this.name = name;
        this.description = description;
        this.likes = 0;
        this.labels = new ArrayList<>();
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

}
