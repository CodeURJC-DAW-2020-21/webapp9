package urjc.ugc.ultragamecenter.restControllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.components.UserComponent;
import urjc.ugc.ultragamecenter.controllers.*;
import urjc.ugc.ultragamecenter.models.User;

@RestController
public class ComandsRestController {
    
    @Autowired
    UserController uController;

    @Autowired
    AdminController aController;

    @Autowired
    EventsController eController;

    @Autowired
    LoginController lController;

    @Autowired
    ReservationController rController;
    
    @Autowired
    UserComponent uComponent;

    @GetMapping("api/login")
    public User login(@RequestParam String email, @RequestParam String password){
        return lController.logUsr(email, password);
    }

    @GetMapping("api/reservate")
    public void reservateTable(@RequestParam String type, @RequestParam String day, @RequestParam String hour,@RequestParam(required = false) String email) throws MessagingException{
        rController.reserveTable(type,day,hour,email);
    }

    @GetMapping("api/getEventData")
    public Double getEventData(@RequestParam Integer id){
        return eController.getData(id);
    }

}