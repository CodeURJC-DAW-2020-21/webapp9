package urjc.ugc.ultragamecenter.rest_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.api_models.API_user;
import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.services.UserService;

@RestController
public class UserRestController {

    @Autowired
    UserService uService;

    @Autowired
    UserComponent uComponent;

    @GetMapping("api/seeUser")
    public API_user getUser() {
        return new API_user(uComponent.getLoggedUser());
    }

    @PostMapping("api/createUsers")
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String lastName, @RequestParam String password, @RequestParam String email) {
        uService.createNewUser(name, lastName, password, email);
        User user = uService.findByEmail(email);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "https://localhost:8443/api/seeUsers?email=" + user.getEmail());
        return ResponseEntity.ok().headers(responseHeaders).body("User created");
    }

    @PutMapping("api/editUsers")
    public API_user editUser(@RequestParam String email, @RequestParam String name, @RequestParam String lastName,
            @RequestParam String password, @RequestParam String emailNew) {
        return new API_user(uService.updateUser(email, name, lastName, password, emailNew));
    }

}
