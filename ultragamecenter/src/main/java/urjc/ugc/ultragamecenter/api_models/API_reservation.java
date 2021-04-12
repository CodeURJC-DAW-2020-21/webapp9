package urjc.ugc.ultragamecenter.api_models;


import urjc.ugc.ultragamecenter.models.TableReservation;

public class API_reservation {

    private Long id_table = 0L;
    private String referenced_code = "No existe esta reserva";
    private Integer hour = 0;

    public API_reservation(TableReservation t) {
        if (t != null) {
            id_table = t.getId_table();
            referenced_code = t.getReferenced_code();
            hour = t.getHour();
        }
    }

    public Long getId_table() {
        return id_table;
    }

    public String getReferenced_code() {
        return referenced_code;
    }

    public Integer getHour() {
        return hour;
    }
}
