package urjc.ugc.ultragamecenter.restControllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.models.TableReservation;
import urjc.ugc.ultragamecenter.services.TableReservationService;

@RestController
public class TableReservationRestController {
    
    @Autowired
    TableReservationService trService;

    @GetMapping("api/reservations")
    public List<TableReservation> all(@RequestParam(defaultValue = "0") Integer page){
        return trService.getAll();
    }

    @GetMapping("api/seeReservation")
    public TableReservation getReservation(@RequestParam Long id){
        return trService.getByid(id);
    }

    @DeleteMapping("api/deleteReservations")
    public void deleteEvent(@RequestParam Long id){
        trService.delete(id);
    }

    @PostMapping("api/createReservations")
    public ResponseEntity<String> createReservation(){
        return null;
    }

    @PutMapping("api/editReservation")
    public TableReservation editEvent(){
        return null;
    }

}
