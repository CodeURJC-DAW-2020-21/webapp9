package urjc.ugc.ultragamecenter.rest_controllers;

import java.net.MalformedURLException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import urjc.ugc.ultragamecenter.security.jwt.UserLoginService;

import urjc.ugc.ultragamecenter.models.Event;
import urjc.ugc.ultragamecenter.services.EventService;
import urjc.ugc.ultragamecenter.services.ImageService;
import urjc.ugc.ultragamecenter.services.UserService;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.requests.UserDTO;
import urjc.ugc.ultragamecenter.security.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    UserService uService;

    @Autowired
    EventService eService;

    @Autowired
    UserDetailsServiceImpl uDetails;

    @Autowired
    ImageService imgService;

    @Autowired
	AuthenticationManager authManager;

    @Autowired
	UserLoginService ulService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/me")
	public ResponseEntity<User> me(HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		
		if(principal != null) {
			return ResponseEntity.ok(uService.findByEmail(principal.getName()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserDTO u) {
        User newUser = new User(u);
        if (uService.findByEmail(newUser.getEmail()) != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        uService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/")
    public ResponseEntity<User> editUser(@RequestBody UserDTO editedUser) {
        User lastUser = uService.findByEmail(editedUser.getEmail());
        if (lastUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if (!uDetails.getEmail().equals(lastUser.getEmail())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        lastUser.edit(editedUser);
        uService.save(lastUser);
        return new ResponseEntity<>(lastUser, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/image",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestBody MultipartFile imageFile) {
        User user = uService.findByEmail(uDetails.getEmail());
        URI location = fromCurrentRequest().build().toUri();
        String aux= imgService.uploadImage(imageFile);
        user.setProfileSrc(aux);
        uService.save(user);
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/image")
    public ResponseEntity<Object> downloadImage() throws MalformedURLException {
        return this.imgService.createResponseFromImage(uService.findByEmail(uDetails.getEmail()));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/recomendatedEvents")
    public Collection<Event> getLikedEvents() {
        return uService.getRecomendatedEvents(5);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/likedEvents")
    public Collection<Event> getRecomendatedEvents() {
        User aux =  uService.findByEmail(uDetails.getEmail());
        if(aux!=null){
            return eService.transform(aux.getEventsLiked());
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/myReservates")
    public Collection<String> reservations(@RequestParam Integer page) {
        return uService.findByEmail(uDetails.getEmail()).getReferencedCodes();
    }
}
