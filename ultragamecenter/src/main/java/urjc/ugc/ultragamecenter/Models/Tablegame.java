package urjc.ugc.ultragamecenter.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<Integer> state = new ArrayList<>();
    private Date date;

    public Tablegame(){}

    public Tablegame(String type,String date) { //YYYY-MM-DD
        this.type = type;
        for (int i = 0; i < 9; i++) {
            this.state.add(0);
        }
        this.date=Date.valueOf(date);
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

    public List<Integer> getState(){
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
