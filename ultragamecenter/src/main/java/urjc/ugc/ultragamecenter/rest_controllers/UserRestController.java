package urjc.ugc.ultragamecenter.rest_controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.Event;
import urjc.ugc.ultragamecenter.services.EventService;
import urjc.ugc.ultragamecenter.services.ImageService;
import urjc.ugc.ultragamecenter.services.UserService;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.requests.LoginRequest;
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    UserService uService;

    @Autowired
    EventService eService;

    @Autowired
    UserComponent uComponent;

    @Autowired
    ImageService imgService;

    private static final String USER_FOLDER="user";

    @GetMapping("/")
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(uComponent.getLoggedUser(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@PathVariable User newUser) {
        if(uService.findByEmail(newUser.getEmail())==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        uService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<User> editUser(@PathVariable User editedUser) {
        User lastUser = uService.findByEmail(editedUser.getEmail());
        if(lastUser==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if(uComponent.getLoggedUser().getEmail().equals(lastUser.getEmail())){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        editedUser.setId(lastUser.getId());
        uService.delete(lastUser);
        uService.save(editedUser);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        User u=uService.logUsr(email, password);
        if(u!=null){
            return new ResponseEntity<>(u, HttpStatus.OK);
        }
        return new ResponseEntity<>(u, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> login(){
        uComponent.logOut();
        return new ResponseEntity<>("Has cerrado sesión", HttpStatus.OK);
    }

    @RequestMapping(
    path = "/image", 
    method = RequestMethod.POST, 
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage( @RequestParam MultipartFile imageFile) throws IOException {
        User user = uComponent.getLoggedUser();
        URI location = fromCurrentRequest().build().toUri();
        user.setProfileSrc(location.toString());
        uService.save(user);
        imgService.uploadImage(imageFile);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/image")
	public ResponseEntity<Object> downloadImage() throws MalformedURLException {
        return this.imgService.createResponseFromImage(uComponent.getLoggedUser());
	}

    @GetMapping("/likedEvents")
    public Collection<Event> getLikedEvents(){
        return uService.getRecomendatedEvents(5);
    }

    @GetMapping("/recomendatedEvents")
    public Collection<Event> getRecomendatedEvents(){
        return eService.transform(uComponent.getLoggedUser().getEventsLiked());
    }

    @GetMapping("/myReservates")
    public Collection<String> reservations(@RequestParam Integer page) {
        return uComponent.getLoggedUser().getReferencedCodes();
    }
}
