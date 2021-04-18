package urjc.ugc.ultragamecenter.rest_controllers;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.api_models.APIreservation;
import urjc.ugc.ultragamecenter.api_models.APIreservations;
import urjc.ugc.ultragamecenter.Components.UserComponent;
import urjc.ugc.ultragamecenter.Controllers.*;
import urjc.ugc.ultragamecenter.Models.TableReservation;
import urjc.ugc.ultragamecenter.Services.TableReservationService;

@RestController
public class ReservationRestController {

    @Autowired
    UserComponent uComponent;

    @Autowired
    TableReservationService trService;

    @PostMapping("api/reservate")
    public ResponseEntity<APIreservation> reservateTable(@RequestParam String type, @RequestParam String day,
            @RequestParam String hour, @RequestParam(required = false) String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (email==null && !uComponent.isLoggedUser()) {
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

    @GetMapping("api/myReservates")
    public ResponseEntity<List<APIreservations>> reservations(@RequestParam Integer page) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isLoggedUser()) {
            return ResponseEntity.ok().headers(responseHeaders).body(APIreservations.transform3(trService.getPageReservations(page,5)));
        }
        ArrayList<APIreservations> a = new ArrayList<>();
        a.add(new APIreservations("No estas logeado"));
        return ResponseEntity.badRequest().headers(responseHeaders).body(a);
    }

}
