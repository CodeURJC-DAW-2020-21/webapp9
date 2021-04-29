package urjc.ugc.ultragamecenter.rest_controllers;

import org.springframework.http.HttpStatus;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.requests.ReservateTableRequest;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;
import urjc.ugc.ultragamecenter.services.TableReservationService;

@RestController
@RequestMapping("/api/reservate")
public class ReservationRestController {

    @Autowired
    UserDetailsServiceImpl uDetails;

    @Autowired
    TableReservationService trService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<TableReservation> reservateTable(@RequestBody ReservateTableRequest tableRequest) {
        System.out.println(tableRequest);
        if (tableRequest.getEmail()==null && !uDetails.idLoggedUser()) {
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
