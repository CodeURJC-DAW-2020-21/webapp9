package urjc.ugc.ultragamecenter.restControllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import urjc.ugc.ultragamecenter.api_models.API_eventDATA;
import urjc.ugc.ultragamecenter.api_models.API_reservation;
import urjc.ugc.ultragamecenter.api_models.API_user;
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
    public API_user login(@RequestParam String email, @RequestParam String password){
        return new API_user(lController.logUsr(email, password));
    }

    @GetMapping("api/reservate")
    public API_reservation reservateTable(@RequestParam String type, @RequestParam String day, @RequestParam String hour,@RequestParam(required = false) String email) throws MessagingException{
        return new API_reservation(rController.reserveTable(type,day,hour,email));
    }

    @GetMapping("api/getEventData")
    public API_eventDATA getEventData(@RequestParam Integer id){
        return new API_eventDATA(eController.getData(id));
    }





}