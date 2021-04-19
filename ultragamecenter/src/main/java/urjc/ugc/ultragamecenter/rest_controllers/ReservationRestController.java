package urjc.ugc.ultragamecenter.rest_controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.controllers.*;
import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.requests.ReservateTableRequest;
import urjc.ugc.ultragamecenter.services.TableReservationService;

@RestController
@RequestMapping("/api/reservate")
public class ReservationRestController {

    @Autowired
    UserComponent uComponent;

    @Autowired
    TableReservationService trService;

    @PostMapping("/")
    public ResponseEntity<TableReservation> reservateTable(@RequestBody ReservateTableRequest tableRequest) {
        if (tableRequest.getEmail()==null && !uComponent.isLoggedUser()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            TableReservation t = trService.reserveTable(tableRequest.getType(), tableRequest.getDay(), tableRequest.getHour().toString(), tableRequest.getEmail());
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
    }

    

    


}
