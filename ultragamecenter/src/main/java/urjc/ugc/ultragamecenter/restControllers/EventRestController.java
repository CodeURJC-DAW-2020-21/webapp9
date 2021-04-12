package urjc.ugc.ultragamecenter.restControllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("api/getevents")
    public List<API_events> all(){
        return API_events.transform(eService.getAllEvents());
    }

    @GetMapping("api/seeEvent")
    public API_event getEvent(@RequestParam Long id){
        return new API_event(eService.getByid(id));
    }

    @DeleteMapping("api/deleteEvent")
    public void deleteEvent(@RequestParam Long id){
        Event e=eService.getByid(id);
        if(e!=null){
            eService.delete(eService.getByid(id));
        }
        
    }

    @PostMapping("api/createEvents")
    public ResponseEntity<String> createEvent(@RequestParam String name, @RequestParam String description,@RequestParam String date,@RequestParam Integer capacity){
        Event createdEvent = eService.createNewEvent(name,description,date,capacity);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "https://localhost:8443/api/seeEvent?id="+String.valueOf(createdEvent.getId()));
        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body("Event created");
    }

    @PutMapping("api/editEvent")
    public API_event editEvent(@RequestParam Long id,@RequestParam String name, @RequestParam String description, @RequestParam String date,@RequestParam Integer capacity){
        return new API_event(eService.updateEvent(id,name,description,date,capacity));
    }

    @GetMapping("api/getEventData")
    public API_eventDATA getEventData(@RequestParam Long id){
        return new API_eventDATA(eService.getByid(id));
    }

}
