package urjc.ugc.ultragamecenter.rest_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import urjc.ugc.ultragamecenter.api_models.APIuser;
import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.services.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    UserService uService;

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
    public ResponseEntity<APIuser> editUserPassword(@RequestParam String password, @RequestParam String newPassword) {
        HttpHeaders responseHeaders = new HttpHeaders();
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
    public ResponseEntity<APIuser> login(@RequestParam String email, @RequestParam String password){
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
}
