package urjc.ugc.ultragamecenter.api_models;

import java.util.ArrayList;
import java.util.List;

import urjc.ugc.ultragamecenter.models.TableReservation;

public class API_reservations {

    private Long id = 0L;
    private String referenced_code = "Esta reserva no existe";

    public API_reservations(TableReservation t, Boolean b) {
        if (t != null && b) {
            id = t.getID();
            referenced_code = t.getReferenced_code();
        }
        if (!b && t != null) {
            id = 0L;
            referenced_code = "Necesitas ser administrador";
        }
    }

    public Long getId() {
        return id;
    }

    public String getReferenced_code() {
        return referenced_code;
    }

    public static ArrayList<API_reservations> transform(List<TableReservation> t, Boolean b) {
        ArrayList<API_reservations> a = new ArrayList<>();
        if (b) {
            for (TableReservation tr : t) {
                a.add(new API_reservations(tr,b));
            }
        } else{
            a.add(new API_reservations(new TableReservation(),b));
        }
        return a;
    }
}
