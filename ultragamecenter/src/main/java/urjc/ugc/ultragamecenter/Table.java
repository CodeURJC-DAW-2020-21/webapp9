package urjc.ugc.ultragamecenter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private final String possibleTypes[]={"PS4","PC","XBOX"};
    private Boolean free;

    public Table(String type, Boolean free) {
        this.type = type;
        this.free = free;
    }

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




