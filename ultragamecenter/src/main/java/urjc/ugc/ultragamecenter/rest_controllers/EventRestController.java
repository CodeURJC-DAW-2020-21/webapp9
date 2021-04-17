package urjc.ugc.ultragamecenter.rest_controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.api_models.*;
import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.EventService;

@RestController
public class EventRestController {

    @Autowired
    EventService eService;

    @Autowired
    UserComponent uComponent;

    @GetMapping("api/events")
    public ResponseEntity<List<APIevents>> all(@RequestParam Integer page) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(APIevents.transform(eService.getPageEvents(page,5)));
    }

    @GetMapping("api/event")
    public ResponseEntity<APIevent> getEvent(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(eService.getByid(id)));
    }

    @DeleteMapping("api/byeEvent")
    public ResponseEntity<APIevent> deleteEvent(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isAdmin()) {
            if (eService.getByid(id) != null) {
                return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(eService.deleteID(id)));
            } else {
                return ResponseEntity.badRequest().headers(responseHeaders).body(new APIevent("No existe ese evento"));
            }
        } else {
            return ResponseEntity.badRequest().headers(responseHeaders).body(new APIevent("No tienes permisos para esa acción"));
        }
    }

    @PostMapping("api/newEvent")
    public ResponseEntity<APIevent> createEvent(@RequestParam String name, @RequestParam String description,
            @RequestParam String date, @RequestParam Integer capacity, @RequestParam String labels) {
        MultipartFile[] pack = { null };
        Event createdEvent = eService.createNewEvent(name, description, null, pack, date, capacity, labels);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (createdEvent != null) {
            responseHeaders.set("Location", "https://localhost:8443/api/seeEvent?id=" + createdEvent.getId());
            return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(createdEvent));
        } else {
            return ResponseEntity.badRequest().headers(responseHeaders).body(new APIevent("No tienes permisos para esa acción"));
        }
    }

    @PutMapping("api/otherEvent")
    public ResponseEntity<APIevent> editEvent(@RequestParam Long id, @RequestParam(required = false) String name,
            @RequestParam(required = false) String description, @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer capacity) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isAdmin()) {
            return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(eService.updateEvent(id, name, description, date, capacity,null,null)));
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(new APIevent("No tienes permisos para esa acción"));
    }

    @GetMapping("api/eventData")
    public ResponseEntity<APIeventDATA> getEventData(@RequestParam Long id) {
        Event e = eService.getByid(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        if(e!=null){
            return ResponseEntity.ok().headers(responseHeaders).body(new APIeventDATA(e));
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(new APIeventDATA("No existe ese evento"));
    }

    @GetMapping("api/like")
    public ResponseEntity<String> like(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if( eService.like(id)){
            return ResponseEntity.ok().headers(responseHeaders).body("Has podido dar like");
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body("No has podido dar like");
    }

}
