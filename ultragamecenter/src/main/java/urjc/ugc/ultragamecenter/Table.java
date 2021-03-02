package urjc.ugc.ultragamecenter;

import javax.persistence.Entity;

@Entity
public class Table {
    private Long id;
    private String type;
    private final String possibleTypes[]={"PS4","PC","XBOX"};
    private Boolean free;



    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public Boolean getFree(){
        return this.free;
    }

    public void setFree(Boolean free){
        this.free = free;
    }

}




