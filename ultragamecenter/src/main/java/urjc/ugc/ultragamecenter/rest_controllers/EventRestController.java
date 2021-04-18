package urjc.ugc.ultragamecenter.rest_controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.api_models.*;
import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.EventService;

@RestController
@RequestMapping("/api")
public class EventRestController {

    @Autowired
    EventService eService;

    @Autowired
    UserComponent uComponent;

    @GetMapping("/events")
    public ResponseEntity<List<APIevents>> all(@RequestParam Integer page) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(APIevents.transform(eService.getPageEvents(page, 5)));
    }

    @GetMapping("/event")
    public ResponseEntity<APIevent> getEvent(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(eService.getByid(id)));
    }

    @DeleteMapping("/event")
    public ResponseEntity<APIevent> deleteEvent(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isAdmin()) {
            if (eService.getByid(id) != null) {
                return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(eService.deleteID(id)));
            } else {
                return ResponseEntity.badRequest().headers(responseHeaders).body(new APIevent("No existe ese evento"));
            }
        } else {
            return ResponseEntity.badRequest().headers(responseHeaders)
                    .body(new APIevent("No tienes permisos para esa acción"));
        }
    }

    @PostMapping("/event")
    public ResponseEntity<APIevent> createEvent(@RequestParam String name, @RequestParam String description,
            @RequestParam String date, @RequestParam Integer capacity, @RequestParam String labels) throws IOException {
        MultipartFile[] pack = { null };
        Event createdEvent = eService.createNewEvent(name, description, null, pack, date, capacity, labels);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (createdEvent != null) {
            responseHeaders.set("Location", "https://localhost:8443/api/seeEvent?id=" + createdEvent.getId());
            return ResponseEntity.ok().headers(responseHeaders).body(new APIevent(createdEvent));
        } else {
            return ResponseEntity.badRequest().headers(responseHeaders)
                    .body(new APIevent("No tienes permisos para esa acción"));
        }
    }

    @PutMapping("/event")
    public ResponseEntity<APIevent> editEvent(@RequestParam Long id, @RequestParam(required = false) String name,
            @RequestParam(required = false) String description, @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer capacity) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isAdmin()) {
            return ResponseEntity.ok().headers(responseHeaders)
                    .body(new APIevent(eService.updateEvent(id, name, description, date, capacity, null, null)));
        }
        return ResponseEntity.badRequest().headers(responseHeaders)
                .body(new APIevent("No tienes permisos para esa acción"));
    }

    @GetMapping("/eventData")
    public ResponseEntity<APIeventDATA> getEventData(@RequestParam Long id) {
        Event e = eService.getByid(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (uComponent.isAdmin()) {
            if (e != null) {
                return ResponseEntity.ok().headers(responseHeaders).body(new APIeventDATA(e));
            }
            return ((BodyBuilder) ResponseEntity.notFound().headers(responseHeaders))
                    .body(new APIeventDATA("No existe ese evento"));
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(new APIeventDATA("No tienes permisos para esta acción"));
    }

    

    @PostMapping("/like")
    public ResponseEntity<String> like(@RequestParam Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (eService.like(id)) {
            return ResponseEntity.ok().headers(responseHeaders).body("Has podido dar like");
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body("No has podido dar like");
    }

}
