package urjc.ugc.ultragamecenter.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.sql.Date;

@Entity
@Table(name="Reservation")
public class TableReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long id_table;
    private String referenced_code;
    private Date date;
    private Integer hour;

    public TableReservation(){}
    public TableReservation(Long id_table, String referenced_code, Date date, Integer hour) {
        this.id_table = id_table;
        this.referenced_code = referenced_code;
        this.date = date;
        this.hour = hour;
    }

    public Long getId_table() {
        return id_table;
    }

    public String getReferenced_code() {
        return referenced_code;
    }

    public void setReferenced_code(String referenced_code) {
        this.referenced_code = referenced_code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }


    @Override
	public String toString() {
		return "Table reservation [id=" + id + ",id_table=" + id_table + ", Referenced_code=" + referenced_code + ", date=" + date +", hour="+hour+"]";
	}
    
    
}
