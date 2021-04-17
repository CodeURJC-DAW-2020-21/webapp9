package urjc.ugc.ultragamecenter.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="Reservation")
public class TableReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idTable;
    private String referencedCode;
    private Integer hour;

    public TableReservation(){}
    public TableReservation(Long idTable, String referencedCode, Integer hour) {
        this.idTable = idTable;
        this.referencedCode = referencedCode;
        this.hour = hour;
    }

    public Long getIdTable() {
        return idTable;
    }

    public String getReferencedCode() {
        return referencedCode;
    }

    public void setReferencedCode(String referencedCode) {
        this.referencedCode = referencedCode;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }


    @Override
	public String toString() {
		return "Table reservation [id=" + id + ",id_table=" + idTable + ", Referenced_code=" + referencedCode +", hour="+hour+"]";
	}
    public Long getID() {
        return this.id;
    }
    
    
}
