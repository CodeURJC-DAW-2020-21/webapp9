package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.TableReservation;

public class APIreservations {

    private Long id = 0L;
    private String referencedCode = "Esta reserva no existe";

    public APIreservations(TableReservation t) {
        if (t != null) {
            id = t.getID();
            referencedCode = t.getReferencedCode();
        }
        
    } 

    public APIreservations(String t){
        referencedCode=t;
    }

    public APIreservations(String s, Long l){
        this.id=l;
        this.referencedCode = s;
    }

    public Long getId() {
        return id;
    }

    public String getReferencedCode() {
        return referencedCode;
    }

    public static List<APIreservations> transform(List<TableReservation> t, boolean b) {
        ArrayList<APIreservations> a = new ArrayList<>();
        if (b) {
            for (TableReservation tr : t) {
                a.add(new APIreservations(tr));
            }
        } else{
            a.add(new APIreservations(new TableReservation()));
        }
        return a;
    }

    public static List<APIreservations> transform2(List<String>list){
        ArrayList<APIreservations> a= new ArrayList<>();
        for(String s:list){
            a.add(new APIreservations(s,0L));
        }
        return a;
    }
}
