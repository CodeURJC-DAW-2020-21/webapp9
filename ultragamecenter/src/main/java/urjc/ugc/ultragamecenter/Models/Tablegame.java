package urjc.ugc.ultragamecenter.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Table_Games")
public class Tablegame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private static String possibleTypes[]={"PS4","PC","XBOX"};
    private Boolean free;

    public Tablegame(){}

    public Tablegame(String type, Boolean free) {
        this.type = type;
        this.free = free;
    }

    public static String getRandomType(){
        int c= (int)(Math.random()*3);
        return possibleTypes[c];
    }

    public static String[] getTypes() {
        return possibleTypes;
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

    @Override
	public String toString() {
		return "Tabel game [id=" + id + ", Type=" + type + ", free=" + free +"]";
	}


}




