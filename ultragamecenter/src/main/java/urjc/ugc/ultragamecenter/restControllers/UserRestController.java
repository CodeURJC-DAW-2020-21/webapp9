package urjc.ugc.ultragamecenter.restControllers;

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

import urjc.ugc.ultragamecenter.models.User;
import urjc.ugc.ultragamecenter.services.UserService;

@RestController
public class UserRestController {
    
    @Autowired
    UserService uService;

    @GetMapping("api/users")
    public List<User> all(@RequestParam(defaultValue = "1") Integer page){
        return uService.findAll(page,3);
    }

    @GetMapping("api/seeUser")
    public User getUser(@RequestParam String email){
        return uService.findByEmail(email);
    }

    @DeleteMapping("api/deleteUsers")
    public void deleteUser(@RequestParam Integer id) throws Exception{
        uService.deleteById(id);
    }

    @PostMapping("api/createUsers")
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String lastName, 
    @RequestParam String password, @RequestParam String email){
        uService.createNewUser(name, lastName, password, email);
        User user = uService.findByEmail(email);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "https://localhost:8443/api/seeUsers?email="+user.getEmail());
        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body("User created");
    }

    @PutMapping("api/editUsers")
    public User editUser(@RequestParam String email,@RequestParam String name, @RequestParam String lastName, 
    @RequestParam String password, @RequestParam String emailNew){
        return uService.updateUser(email,name,lastName,password,emailNew);
    }

}
