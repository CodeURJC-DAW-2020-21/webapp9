package urjc.ugc.ultragamecenter.Models;

import java.util.ArrayList;

import javax.persistence.Column;
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
    private ArrayList<Integer> state = new ArrayList<Integer>();

    public Tablegame(){}

    public Tablegame(String type) {
        this.type = type;
        for (int i = 0; i < 9; i++) {
            this.state.add(0);
        }
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

    public ArrayList<Integer> getState(){
        return this.state;
    }

    public void setState(Integer pos, Integer state){
        this.state.set(pos, state);
    }

    @Override
	public String toString() {
		return "Tabel game [id=" + id + ", Type=" + type + ", free=" + state +"]";
	}


}




