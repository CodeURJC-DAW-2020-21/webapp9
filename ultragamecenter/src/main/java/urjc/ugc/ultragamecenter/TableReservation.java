package urjc.ugc.ultragamecenter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Entity
public class TableReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long id_table;
    private String referenced_code;
    private Date date;
    private Date hour;

    
    public TableReservation(Long id_table, String referenced_code, Date date, Date hour) {
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

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }



    
    
}
