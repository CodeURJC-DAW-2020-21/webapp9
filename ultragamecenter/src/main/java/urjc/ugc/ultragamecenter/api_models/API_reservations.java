package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.TableReservation;

public class API_reservations {
    private Long id;
    private String referenced_code;

    public API_reservations(TableReservation t){
        id=t.getID();
        referenced_code=t.getReferenced_code();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenced_code() {
        return referenced_code;
    }

    public void setReferenced_code(String referenced_code) {
        this.referenced_code = referenced_code;
    }

    public static ArrayList<API_reservations> transform(List<TableReservation> t){
        ArrayList<API_reservations> a=new ArrayList<>();
        for(TableReservation tr:t){
            a.add(new API_reservations(tr));
        }
        return a;
    }
}
