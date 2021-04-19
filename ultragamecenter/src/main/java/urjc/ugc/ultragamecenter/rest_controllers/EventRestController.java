package urjc.ugc.ultragamecenter.rest_controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.services.EventService;

@RestController
@RequestMapping("/api/event")
public class EventRestController {

    @Autowired
    EventService eService;

    @GetMapping("/")
	public Collection<Event> getEvents(@RequestParam Integer page) {
		return eService.getPageEvents(page,3).getContent();
	}

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event e = eService.getByid(id);
        if(e!=null){
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@RequestParam Long id) {
        eService.delete(eService.getByid(id));
	    return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/")
    public Event createBook(@RequestBody Event event) throws IOException {
        eService.save(event);
        return event;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> editEvent(@RequestParam Long id, @RequestBody Event newEvent) throws IOException {
        Event e = eService.getByid(id);
        if(e!=null){
            eService.delete(e);
            newEvent.setID(id);
            eService.save(newEvent);
            return new ResponseEntity<>(newEvent, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/DATA/{id}")
    public ResponseEntity<Object> getEventData(@RequestParam Long id) {
        Event e = eService.getByid(id);
        if (e != null) {
            return new ResponseEntity<>(e.getDATA(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }



    @PostMapping("/{id}/like")
    public ResponseEntity<String> like(@RequestParam Long id) {
        if (eService.like(id)) {
            return new ResponseEntity<>("Well Done", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot like",HttpStatus.BAD_REQUEST);
        }
    }

}
