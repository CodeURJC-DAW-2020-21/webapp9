package urjc.ugc.ultragamecenter.requests;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class EventDTO {


    private String name;
    
    private String description;

    private Date date;

    private ArrayList<String> lavels;

    public EventDTO(String name, String description, Date date, ArrayList<String> lavels,Integer capacity) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.lavels = lavels;
        this.capacity = capacity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getLavels() {
        return lavels;
    }

    public void setLavels(ArrayList<String> lavels) {
        this.lavels = lavels;
    }



    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    private Integer capacity;
}
