package urjc.ugc.ultragamecenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AplicationController {
    
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("nombre", "Index");
        return "IndexTemplate";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute("nombre2", "Admin");
        return "AdminTemplate";
    }

    @GetMapping("/user")
    public String getUser(Model model) {
        model.addAttribute("nombre3", "User Page");
        return "UserTemplate";
    }

    @GetMapping("/register")
    public String getLoginRegister(Model model) {
        model.addAttribute("nombre4", "Register Page");
        return "LoginRegisterTemplate";
    }

    @GetMapping("/reservation")
    public String getReservation(Model model) {
        model.addAttribute("nombre5", "Reservation Page");
        return "ReservationTemplate";
    }

    @GetMapping("/singleevent")
    public String getSingleEvent(Model model) {
        model.addAttribute("nombre6", "SingleEvent Page");
        return "SingleEventTemplate";
    }

    @GetMapping("/events")
    public String getEvents(Model model) {
        model.addAttribute("nombre", "Events Page");
        return "EventsTemplate";
    }

    @GetMapping("/add") // Map ONLY POST Requests
    public String add(Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Event event = new Event("Fornite","fornite x marvel",2);
        //eventItem event = new eventItem();
        //event.setName("Fornite");
        //event.setDesc("fornite x marvel");
        eventRepository.save(event);
        return "Save";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        List<Event> lista;
        lista = eventRepository.findAll();
        model.addAttribute("pepa",lista.get(0).getName());
        model.addAttribute("pepo", lista.get(0).getDescription());
        return "test";
    }

}