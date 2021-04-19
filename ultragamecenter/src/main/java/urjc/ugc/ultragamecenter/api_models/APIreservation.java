package urjc.ugc.ultragamecenter.api_models;


import urjc.ugc.ultragamecenter.models.TableReservation;

public class APIreservation {

    private Long idTable = 0L;
    private String referencedCode = "No existe esta reserva";
    private Integer hour = 0;

    public APIreservation(TableReservation t) {
        if (t != null) {
            idTable = t.getIdTable();
            referencedCode = t.getReferencedCode();
            hour = t.getHour();
        }
    }

    public APIreservation(String string) {
        referencedCode=string;
    }

    public Long getIdTable() {
        return idTable;
    }

    public String getReferencedCode() {
        return referencedCode;
    }

    public Integer getHour() {
        return hour;
    }
}
