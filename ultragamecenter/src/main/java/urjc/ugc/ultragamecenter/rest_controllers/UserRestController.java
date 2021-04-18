package urjc.ugc.ultragamecenter.rest_controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.api_models.APIevents;
import urjc.ugc.ultragamecenter.api_models.APIuser;
import urjc.ugc.ultragamecenter.Components.UserComponent;
import urjc.ugc.ultragamecenter.Models.Event;
import urjc.ugc.ultragamecenter.Models.User;
import urjc.ugc.ultragamecenter.Services.EventService;
import urjc.ugc.ultragamecenter.Services.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    UserService uService;

    @Autowired
    EventService eService;

    @Autowired
    UserComponent uComponent;

    @GetMapping("/user")
    public ResponseEntity<APIuser> getUser() {
        HttpHeaders responseHeaders = new HttpHeaders();
        if(uComponent.isLoggedUser()){
            return ResponseEntity.ok().headers(responseHeaders).body(new APIuser(uComponent.getLoggedUser()));
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(new APIuser("No estas logeado"));
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String password, @RequestParam String email) {
        User user = uService.createNewUser(name, lastName, password, email);
        HttpHeaders responseHeaders = new HttpHeaders();
        if(user==null){
            return ResponseEntity.badRequest().headers(responseHeaders).body("El correo ya existe");
        }
        return ResponseEntity.ok().headers(responseHeaders).body("Usuario creado");
    }

    @PutMapping("/user")
    public ResponseEntity<APIuser> editUser(@RequestParam(required = false) String name, @RequestParam(required = false) String lastName) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if(uComponent.isLoggedUser()){
            return ResponseEntity.ok().headers(responseHeaders).body(new APIuser(uService.updateUser(name, lastName, null)));
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(new APIuser("No estas logeado"));
    }

    @PutMapping("/pasword")
    public ResponseEntity<APIuser> editUserPassword(HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        String password = request.getHeader("password");
        String newPassword = request.getHeader("newPassword");
        User u=uComponent.getLoggedUser();
        if(u==null){
            return ResponseEntity.badRequest().headers(responseHeaders).body(new APIuser("No estas logeado"));
        }
        u= uService.updateUserPassword(password, newPassword);
        if(u==null){
            return ResponseEntity.badRequest().headers(responseHeaders).body(new APIuser("Las contraseñas no coinciden"));
        }
        return ResponseEntity.ok().headers(responseHeaders).body(new APIuser(u));
    }

    @GetMapping("/login")
    public ResponseEntity<APIuser> login(HttpServletRequest request){
        String email = request.getHeader("email");
        String password = request.getHeader("password");
        User u=uService.logUsr(email, password);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (u==null){
            return ResponseEntity.badRequest().headers(responseHeaders).body(new APIuser("No se ha podido logear"));
        }
        return ResponseEntity.ok().headers(responseHeaders).body(new APIuser(u));
    }

    @GetMapping("/logout")
    public ResponseEntity<APIuser> login(){
        uComponent.logOut();
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok().headers(responseHeaders).body(new APIuser("Ya no tienes sesión iniciada"));
    }

    @PutMapping("/image")
    public ResponseEntity<String> setImage(@RequestParam MultipartFile image){
        HttpHeaders responseHeaders = new HttpHeaders();
        if(uComponent.isLoggedUser()){
            uService.updateUser(uComponent.getLoggedUser().getName(), uComponent.getLoggedUser().getLastName(),image);
            return ResponseEntity.ok().headers(responseHeaders).body("Ya tienes una nueva foto preciosa de perfil");
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body("No estas logeado");
    }

    @GetMapping("/likeEvents")
    public ResponseEntity<List<APIevents>> getEventData(@RequestParam Integer Page) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if(uComponent.isLoggedUser()){
            List<Long> x =uComponent.getLoggedUser().getEventsLiked().subList(0+5*Page, 5+5*Page);
            ArrayList<Event> y=new ArrayList<>();
            for(Long l:x){
                y.add(eService.getByid(l));
            }
            Page<Event> e = new PageImpl<>(y);
            return ResponseEntity.ok().headers(responseHeaders).body(APIevents.transform(e));
        }
        ArrayList<APIevents> e=new ArrayList<>();
        e.add(new APIevents("No tienes permisos para esta acción"));
        return ResponseEntity.badRequest().headers(responseHeaders).body(e);
    }

    @GetMapping("/recomendatedEvents")
    public ResponseEntity<List<Event>> getRecomendatedEvents(){
        HttpHeaders responseHeaders = new HttpHeaders();
        if(uComponent.isLoggedUser()){
            return ResponseEntity.ok().headers(responseHeaders).body((uService.getRecomendatedEvents(3)));
        }
        Event tmp = new Event();
        tmp.setName("No estás logeado");
        List<Event> result = new ArrayList<>();
        result.add(tmp);
        return ResponseEntity.badRequest().headers(responseHeaders).body(result);
    }
}
