package urjc.ugc.ultragamecenter.rest_controllers;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.api_models.APIreservation;
import urjc.ugc.ultragamecenter.api_models.APIreservations;
import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.controllers.*;
import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.services.TableReservationService;

@RestController
public class ReservationRestController {

    @Autowired
    UserComponent uComponent;

    @Autowired
    TableReservationService trService;

    @GetMapping("api/reservate")
    public ResponseEntity<APIreservation> reservateTable(@RequestParam String type, @RequestParam String day,
            @RequestParam String hour, @RequestParam(required = false) String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (email.equals("") && !uComponent.isLoggedUser()) {
            return ResponseEntity.badRequest().headers(responseHeaders)
                    .body(new APIreservation("No has dado un email y no estas logeado"));
        } else {
            TableReservation t = trService.reserveTable(type, day, hour, email);
            if (t == null) {
                return ResponseEntity.badRequest().headers(responseHeaders)
                        .body(new APIreservation("No se ha podido reservar el dia: " + day + " a la hora " + hour));
            }
            return ResponseEntity.ok().headers(responseHeaders).body(new APIreservation(t));
        }
    }

    @GetMapping("api/seeReserves")
    public ResponseEntity<List<APIreservations>> reservations() {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isLoggedUser()) {
            return ResponseEntity.ok().headers(responseHeaders)
                    .body(APIreservations.transform2(trService.getMyReservations()));
        }
        ArrayList<APIreservations> a = new ArrayList<>();
        a.add(new APIreservations("No estas logeado"));
        return ResponseEntity.badRequest().headers(responseHeaders).body(a);
    }

}