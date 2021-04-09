package urjc.ugc.ultragamecenter.api_models;

import urjc.ugc.ultragamecenter.models.TableReservation;

public class API_reservation {

    private Long id_table;
    private String referenced_code;
    private Integer hour;

    public API_reservation(TableReservation t){
        id_table=t.getId_table();
        referenced_code=t.getReferenced_code();
        hour=t.getHour();
    }

    public Long getId_table() {
        return id_table;
    }

    public void setId_table(Long id_table) {
        this.id_table = id_table;
    }

    public String getReferenced_code() {
        return referenced_code;
    }

    public void setReferenced_code(String referenced_code) {
        this.referenced_code = referenced_code;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
}
