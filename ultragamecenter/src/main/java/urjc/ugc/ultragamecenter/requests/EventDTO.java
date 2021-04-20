package urjc.ugc.ultragamecenter.requests;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class EventDTO {


    private String name;
    
    private String description;

    private Date date;

    private MultipartFile bannerUrl;

    private ArrayList<String> lavels;

    public EventDTO(String name, String description, Date date, MultipartFile bannerUrl, ArrayList<String> lavels,
            MultipartFile[] gallery, Integer capacity) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.bannerUrl = bannerUrl;
        this.lavels = lavels;
        this.gallery = gallery;
        this.capacity = capacity;
    }

    private MultipartFile[] gallery;

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

    public MultipartFile getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(MultipartFile bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public ArrayList<String> getLavels() {
        return lavels;
    }

    public void setLavels(ArrayList<String> lavels) {
        this.lavels = lavels;
    }

    public MultipartFile[] getGallery() {
        return gallery;
    }

    public void setGallery(MultipartFile[] gallery) {
        this.gallery = gallery;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    private Integer capacity;
}
