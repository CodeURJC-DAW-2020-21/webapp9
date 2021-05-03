package urjc.ugc.ultragamecenter.rest_controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import urjc.ugc.ultragamecenter.models.*;
import urjc.ugc.ultragamecenter.requests.EventDATA;
import urjc.ugc.ultragamecenter.requests.EventDTO;
import urjc.ugc.ultragamecenter.services.EventService;
import urjc.ugc.ultragamecenter.services.ImageService;

import org.springframework.http.MediaType;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/event")
public class EventRestController {

    @Autowired
    EventService eService;

    @Autowired
    ImageService imgService;

    
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
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        eService.delete(eService.getByid(id));
	    return new ResponseEntity<>(null, HttpStatus.OK);
    }

    
    @PostMapping("/")
    public Event createEvent(@RequestBody EventDTO event) throws IOException {
        Event aux= new Event(event);
        ImageService.CREATE_FOLDER_EVENT(aux.getName());
        eService.save(aux);
        return aux;
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Event> editEvent(@PathVariable Long id, @RequestBody EventDTO newEvent) throws IOException {
        Event e = eService.getByid(id);
        if(e!=null){
            e.edit(newEvent);
            eService.giveImages(e, null, null);
            eService.save(e);
            return new ResponseEntity<>(e, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/image/{name}/{type}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable String name,@PathVariable Integer type, @RequestParam("file") MultipartFile imageFile) {
        imgService.uploadImageEvent(imageFile, name,type);
        URI location = fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).build();
    }

    
    @GetMapping("/DATA/{id}")
    public ResponseEntity<Object> getEventData(@PathVariable Long id) {
        Event e = eService.getByid(id);
        if (e != null) {
            return new ResponseEntity<>(new EventDATA(e), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    
    @PostMapping("/like/{id}")
    public ResponseEntity<String> like(@PathVariable Long id) {
        if (eService.like(id)) {
            return new ResponseEntity<>("Well Done", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot like",HttpStatus.BAD_REQUEST);
        }
    }
}
