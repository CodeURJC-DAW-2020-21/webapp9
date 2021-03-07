package urjc.ugc.ultragamecenter.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import urjc.ugc.ultragamecenter.Types.TableType;

@Entity
@Table(name="Table_Games")
public class Tablegame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TableType type;
    private Boolean free;

    public Tablegame(){}

    public Tablegame(TableType type, Boolean free) {
        this.type = type;
        this.free = free;
    }

    public static TableType[] getTypes() {
        return TableType.values();
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public TableType getType(){
        return this.type;
    }

    public void setType(TableType type){
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




